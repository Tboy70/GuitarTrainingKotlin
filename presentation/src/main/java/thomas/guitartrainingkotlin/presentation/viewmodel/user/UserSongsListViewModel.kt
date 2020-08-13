package thomas.guitartrainingkotlin.presentation.viewmodel.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.user.RetrieveSongListByUserId
import thomas.guitartrainingkotlin.presentation.view.datawrapper.SongViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.song.UserSongListViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel

@ExperimentalCoroutinesApi
class UserSongsListViewModel @ViewModelInject constructor(
    private val retrieveSongListByUserId: RetrieveSongListByUserId
) : StateViewModel<UserSongListViewState>() {

    override val currentViewState = UserSongListViewState()

    private var userId: String? = null

    var retrieveUserSongsLiveData = MutableLiveData<List<SongViewDataWrapper>>()

    fun retrieveSongListByUserId() {

        userId?.let {
            viewModelScope.launch {
                try {
                    retrieveSongListByUserId.retrieveSongListByUserId(it)
                        .onStart { viewState.update { loading = true } }
                        .onCompletion { viewState.update { loading = false } }
                        .collect {
                            retrieveUserSongsLiveData.postValue(it.map { song ->
                                SongViewDataWrapper(song)
                            })
                        }
                } catch (e: Exception) {
                    errorLiveEvent.postValue(e)
                }
            }
        }
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }
}