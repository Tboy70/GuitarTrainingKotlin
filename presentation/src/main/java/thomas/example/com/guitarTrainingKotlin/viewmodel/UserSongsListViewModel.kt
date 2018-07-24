package thomas.example.com.guitarTrainingKotlin.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.interactor.user.RetrieveSongsListByUserId
import thomas.example.com.model.Song
import javax.inject.Inject

class UserSongsListViewModel @Inject constructor(private val retrieveSongsListByUserId: RetrieveSongsListByUserId) : ViewModel() {

    var userSongs: List<Song> = ArrayList()

    val finishRetrieveSongs: MutableLiveData<Boolean> = MutableLiveData()

    val refreshList: MutableLiveData<Boolean> = MutableLiveData()

    fun retrieveSongsListByUserId(idUser: String) {
        refreshList.postValue(true)
        retrieveSongsListByUserId.execute(
                onComplete = {
                    refreshList.postValue(false)
                },
                onError = {
                    refreshList.postValue(false)
                    finishRetrieveSongs.postValue(true)
                },
                onNext = {
                    finishRetrieveSongs.postValue(true)

                }, params = RetrieveSongsListByUserId.Params.forList(idUser))
    }

    fun getIdUser(context: Context): String {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(ModuleSharedPrefsImpl.CURRENT_USER_ID, "0")
    }
}