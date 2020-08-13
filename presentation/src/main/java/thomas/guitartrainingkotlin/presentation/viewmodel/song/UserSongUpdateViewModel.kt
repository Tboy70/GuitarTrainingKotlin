package thomas.guitartrainingkotlin.presentation.viewmodel.song

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.song.RetrieveSongById
import thomas.guitartrainingkotlin.domain.interactor.song.UpdateSong
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.presentation.view.datawrapper.SongViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.song.UserSongUpdateViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent

@ExperimentalCoroutinesApi
class UserSongUpdateViewModel @ViewModelInject constructor(
    private val updateSong: UpdateSong,
    private val retrieveSongById: RetrieveSongById
) : StateViewModel<UserSongUpdateViewState>() {

    override val currentViewState = UserSongUpdateViewState()

    private var idSong: String? = null

    val songUpdatedLiveEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val songRetrievedLiveData: MutableLiveData<SongViewDataWrapper> = MutableLiveData()

    fun setIdSong(idSong: String) {
        this.idSong = idSong
    }

    fun checkInformationAndValidateUpdate(titleSong: String, artistSong: String) {
        if (checkInformation(titleSong, artistSong)) {

            idSong?.let { idSong ->
                val song = Song()
                song.idSong = idSong
                song.titleSong = titleSong
                song.artistSong = artistSong

                viewModelScope.launch {
                    try {
                        updateSong.updateSong(song)
                            .onStart { viewState.update { loading = true } }
                            .onCompletion { viewState.update { loading = false } }
                            .collect {
                                songUpdatedLiveEvent.postValue(true)
                            }
                    } catch (e: Exception) {
                        errorLiveEvent.postValue(e)
                    }
                }
            }
        }
    }

    fun getSongById() {

        idSong?.let {
            viewModelScope.launch {
                try {
                    retrieveSongById.retrieveSongById(it)
                        .onStart { viewState.update { loading = true } }
                        .onCompletion { viewState.update { loading = false } }
                        .collect {
                            songRetrievedLiveData.postValue(
                                SongViewDataWrapper(
                                    it
                                )
                            )
                        }
                } catch (e: Exception) {
                    errorLiveEvent.postValue(e)
                }
            }
        }
    }

    private fun checkInformation(nameSong: String, artistSong: String): Boolean {
        return nameSong.isNotEmpty() && artistSong.isNotEmpty()
    }
}