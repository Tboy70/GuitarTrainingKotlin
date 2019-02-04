package thomas.example.com.guitarTrainingKotlin.viewmodel.song

import thomas.example.com.guitarTrainingKotlin.view.state.song.UserSongCreationViewState
import thomas.example.com.guitarTrainingKotlin.viewmodel.base.StateViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.sharedprefs.RetrieveInstrumentModeInSharedPrefs
import thomas.example.com.interactor.sharedprefs.RetrieveUserIdInSharedPrefs
import thomas.example.com.interactor.song.CreateSong
import thomas.example.com.model.Song
import javax.inject.Inject

class UserSongCreationViewModel @Inject constructor(
    private val createSong: CreateSong,
    private val retrieveUserIdInSharedPrefs: RetrieveUserIdInSharedPrefs,
    private val retrieveInstrumentModeInSharedPrefs: RetrieveInstrumentModeInSharedPrefs
) : StateViewModel<UserSongCreationViewState>() {

    override val currentViewState = UserSongCreationViewState()

    private var songToCreate = Song()

    val songCreatedLiveEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val informationNotRightLiveEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun onCleared() {
        super.onCleared()
        createSong.unsubscribe()
        retrieveUserIdInSharedPrefs.unsubscribe()
        retrieveInstrumentModeInSharedPrefs.unsubscribe()
    }

    fun checkInformationAndValidateCreation(titleSong: String, artistSong: String) {
        if (checkInformation(titleSong, artistSong)) {

            viewState.update {
                loading = true
            }

            songToCreate.titleSong = titleSong
            songToCreate.artistSong = artistSong

            //TODO : See if there is a better way
            retrieveInstrumentModeInSharedPrefs()
            retrieveUserIdInSharedPrefs()

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
        } else {
            informationNotRightLiveEvent.postValue(true)
        }
    }

    private fun checkInformation(titleSong: String, artistSong: String): Boolean {
        return !titleSong.isEmpty() && !artistSong.isEmpty()
    }

    private fun retrieveInstrumentModeInSharedPrefs() {
        retrieveInstrumentModeInSharedPrefs.subscribe(
            onSuccess = {
                songToCreate.idInstrument = it
            }, onError = {

            }
        )
    }

    private fun retrieveUserIdInSharedPrefs() {
        retrieveUserIdInSharedPrefs.subscribe(
            onSuccess = {
                songToCreate.userId = it
            }, onError = {

            }
        )
    }
}