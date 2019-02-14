package thomas.guitartrainingkotlin.presentation.viewmodel.user

import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.presentation.view.datawrapper.SongViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.song.UserSongListViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.domain.interactor.user.RetrieveSongListByUserId
import javax.inject.Inject

class UserSongsListViewModel @Inject constructor(
    private val retrieveSongListByUserId: RetrieveSongListByUserId
) : StateViewModel<UserSongListViewState>() {

    override val currentViewState = UserSongListViewState()

    private var userId: String? = null

    var retrieveUserSongsLiveData = MutableLiveData<List<SongViewDataWrapper>>()

    override fun onCleared() {
        super.onCleared()
        retrieveSongListByUserId.unsubscribe()
    }

    fun retrieveSongListByUserId() {
        viewState.update {
            loading = true
        }
        userId?.let { userId ->
            retrieveSongListByUserId.subscribe(
                params = RetrieveSongListByUserId.Params.forList(userId),
                onSuccess = {
                    retrieveUserSongsLiveData.postValue(it.map { song ->
                        SongViewDataWrapper(song)
                    })
                    viewState.update {
                        loading = false
                    }
                },
                onError = { throwable ->
                    errorLiveEvent.postValue(throwable)
                    viewState.update {
                        loading = false
                    }
                }
            )
        }
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }
}