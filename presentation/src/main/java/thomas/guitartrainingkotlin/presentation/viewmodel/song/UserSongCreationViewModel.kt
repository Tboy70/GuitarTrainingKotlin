package thomas.guitartrainingkotlin.presentation.viewmodel.song

import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveInstrumentModeInSharedPrefs
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveUserIdInSharedPrefs
import thomas.guitartrainingkotlin.domain.interactor.song.CreateSong
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.presentation.view.state.song.UserSongCreationViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import javax.inject.Inject

class UserSongCreationViewModel @Inject constructor(
    private val createSong: CreateSong,
    private val retrieveUserIdInSharedPrefs: RetrieveUserIdInSharedPrefs,
    private val retrieveInstrumentModeInSharedPrefs: RetrieveInstrumentModeInSharedPrefs
) : StateViewModel<UserSongCreationViewState>() {

    override val currentViewState = UserSongCreationViewState()

    private var songToCreate = Song()

    val songCreatedLiveEvent = SingleLiveEvent<Boolean>()
    val informationNotRightLiveEvent = SingleLiveEvent<Boolean>()

    fun checkInformationAndValidateCreation(titleSong: String, artistSong: String) {
        if (checkInformation(titleSong, artistSong)) {

            viewState.update {
                loading = true
            }

            songToCreate.titleSong = titleSong
            songToCreate.artistSong = artistSong

            retrieveInstrumentModeInSharedPrefs()
            retrieveUserIdInSharedPrefs()

            compositeDisposable?.add(
                createSong.subscribe(
                    params = CreateSong.Params.toCreate(songToCreate),
                    onComplete = {
                        songCreatedLiveEvent.postValue(true)
                        viewState.update {
                            loading = false
                        }
                    },
                    onError = {
                        errorLiveEvent.postValue(it)
                        viewState.update {
                            loading = false
                        }
                    }
                )
            )
        } else {
            informationNotRightLiveEvent.postValue(true)
        }
    }

    private fun checkInformation(titleSong: String, artistSong: String): Boolean {
        return !titleSong.isEmpty() && !artistSong.isEmpty()
    }

    private fun retrieveInstrumentModeInSharedPrefs() {
        compositeDisposable?.add(
            retrieveInstrumentModeInSharedPrefs.subscribe(
                onSuccess = {
                    songToCreate.idInstrument = it
                }, onError = {
                    errorLiveEvent.postValue(it)
                }
            )
        )
    }

    private fun retrieveUserIdInSharedPrefs() {
        compositeDisposable?.add(
            retrieveUserIdInSharedPrefs.subscribe(
                onSuccess = {
                    songToCreate.userId = it
                }, onError = {
                    errorLiveEvent.postValue(it)
                }
            )
        )
    }
}