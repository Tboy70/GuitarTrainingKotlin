package thomas.guitartrainingkotlin.presentation.viewmodel.song

import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.presentation.view.datawrapper.SongViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.song.UserSongUpdateViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import thomas.guitartrainingkotlin.domain.interactor.song.RetrieveSongById
import thomas.guitartrainingkotlin.domain.interactor.song.UpdateSong
import thomas.guitartrainingkotlin.domain.model.Song
import javax.inject.Inject

class UserSongUpdateViewModel @Inject constructor(
    private val updateSong: UpdateSong,
    private val retrieveSongById: RetrieveSongById
) : StateViewModel<UserSongUpdateViewState>() {

    override val currentViewState = UserSongUpdateViewState()
    private var idSong: String? = null

    val songUpdatedLiveEvent: SingleLiveEvent<Boolean> =
        SingleLiveEvent()
    val songRetrievedLiveData: MutableLiveData<SongViewDataWrapper> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        updateSong.unsubscribe()
        retrieveSongById.unsubscribe()
    }

    fun setIdSong(idSong: String) {
        this.idSong = idSong
    }

    fun checkInformationAndValidateUpdate(titleSong: String, artistSong: String) {
        if (checkInformation(titleSong, artistSong)) {
            viewState.update {
                loading = true
            }
            idSong?.let { idSong ->
                val song = Song()
                song.idSong = idSong
                song.titleSong = titleSong
                song.artistSong = artistSong

                updateSong.subscribe(
                    params = UpdateSong.Params.toUpdate(song),
                    onComplete = {
                        songUpdatedLiveEvent.postValue(true)
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
            }
        }
    }

    fun getSongById() {
        viewState.update {
            loading = true
        }
        this.idSong?.let { idSong ->
            retrieveSongById.subscribe(
                params = RetrieveSongById.Params.toRetrieve(idSong),
                onSuccess = {
                    songRetrievedLiveData.postValue(
                        SongViewDataWrapper(
                            it
                        )
                    )
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
        }
    }

    private fun checkInformation(nameSong: String, artistSong: String): Boolean {
        return nameSong.isNotEmpty() && artistSong.isNotEmpty()
    }
}