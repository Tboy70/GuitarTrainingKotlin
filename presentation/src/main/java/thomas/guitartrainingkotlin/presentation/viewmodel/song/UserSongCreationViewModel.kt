package thomas.guitartrainingkotlin.presentation.viewmodel.song

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveInstrumentModeInSharedPrefs
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveUserIdInSharedPrefs
import thomas.guitartrainingkotlin.domain.interactor.song.CreateSong
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.presentation.view.state.song.UserSongCreationViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent

@ExperimentalCoroutinesApi
class UserSongCreationViewModel @ViewModelInject constructor(
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

            viewModelScope.launch {
                try {
                    songToCreate.titleSong = titleSong
                    songToCreate.artistSong = artistSong

                    retrieveInstrumentModeInSharedPrefs()
                    retrieveUserIdInSharedPrefs()

                    createSong.createSong(songToCreate)
                        .onStart { viewState.update { loading = true } }
                        .onCompletion { viewState.update { loading = false } }
                        .collect {
                            songCreatedLiveEvent.postValue(true)
                        }

                } catch (e: Exception) {
                    errorLiveEvent.postValue(e)
                }
            }

            viewState.update {
                loading = true
            }
        } else {
            informationNotRightLiveEvent.postValue(true)
        }
    }

    private fun checkInformation(titleSong: String, artistSong: String): Boolean {
        return titleSong.isNotEmpty() && artistSong.isNotEmpty()
    }

    private fun retrieveInstrumentModeInSharedPrefs() {

        viewModelScope.launch {
            try {
                retrieveInstrumentModeInSharedPrefs.retrieveInstrumentModeInSharedPrefs()
                    .collect {
                        songToCreate.idInstrument = it
                    }
            } catch (e: Exception) {
                errorLiveEvent.postValue(e)
            }
        }
    }

    private fun retrieveUserIdInSharedPrefs() {

        viewModelScope.launch {
            try {
                retrieveUserIdInSharedPrefs.retrieveUserIdInSharedPrefs()
                    .collect {
                        songToCreate.userId = it ?: "0" // Todo : Check that
                    }
            } catch (e: Exception) {
                errorLiveEvent.postValue(e)
            }
        }
    }
}