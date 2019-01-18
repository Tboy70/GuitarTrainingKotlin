package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.data.manager.SharedPrefsManagerImpl
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.SongViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.user.RetrieveSongsListByUserId
import javax.inject.Inject

class UserSongsListViewModel @Inject constructor(private val retrieveSongsListByUserId: RetrieveSongsListByUserId) :
        ViewModel() {

    var userSongs = MutableLiveData<List<SongViewDataWrapper>>()
    val viewState = MutableLiveData<UserSongsListViewState>()
    val errorEvent =
        SingleLiveEvent<UserSongsListErrorEvent>()
    var errorThrowable: Throwable? = null

    data class UserSongsListViewState(
            var refreshList: Boolean = false
    )

    data class UserSongsListErrorEvent(
            val ERROR_TRIGGERED: Boolean = false
    )

    init {
        // TODO : Launch method here ? Same for other view models ?
    }

    fun retrieveSongsListByUserId(idUser: String) {

        viewState.postValue(UserSongsListViewState(true))

        retrieveSongsListByUserId.subscribe(
                params = RetrieveSongsListByUserId.Params.forList(idUser),
                onSuccess = {
                    userSongs.postValue(it.map { song ->
                        SongViewDataWrapper(song)
                    })
                    viewState.postValue(UserSongsListViewState(false))
                },
                onError = {
                    errorThrowable = it
                    errorEvent.postValue(UserSongsListErrorEvent(true))
                    viewState.postValue(UserSongsListViewState(false))
                }
        )
    }

    fun getIdUser(context: Context): String {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(SharedPrefsManagerImpl.CURRENT_USER_ID, "0")
    }

    override fun onCleared() {
        super.onCleared()
        retrieveSongsListByUserId.unsubscribe()
    }
}