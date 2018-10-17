package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.interactor.user.RetrieveSongsListByUserId
import thomas.example.com.model.Song
import javax.inject.Inject

class UserSongsListViewModel @Inject constructor(private val retrieveSongsListByUserId: RetrieveSongsListByUserId) :
    ViewModel() {

    val finishRetrieveSongs: MutableLiveData<Boolean> = MutableLiveData()
    val refreshList: MutableLiveData<Boolean> = MutableLiveData()

    var userSongs: List<Song> = ArrayList()
    var errorThrowable: Throwable? = null

    fun retrieveSongsListByUserId(idUser: String) {
        refreshList.postValue(true)
        retrieveSongsListByUserId.execute(
            onComplete = {
                refreshList.postValue(false)
            },
            onError = {
                errorThrowable = it
                refreshList.postValue(false)
                finishRetrieveSongs.postValue(false)
            },
            onNext = {
                userSongs = it
                finishRetrieveSongs.postValue(true)

            }, params = RetrieveSongsListByUserId.Params.forList(idUser)
        )
    }

    fun getIdUser(context: Context): String {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(ModuleSharedPrefsImpl.CURRENT_USER_ID, "0")
    }
}