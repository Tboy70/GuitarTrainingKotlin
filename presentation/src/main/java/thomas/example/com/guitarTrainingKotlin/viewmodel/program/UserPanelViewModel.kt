package thomas.example.com.guitarTrainingKotlin.viewmodel.program

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.interactor.user.LogoutUser
import thomas.example.com.interactor.user.RetrieveUserById
import thomas.example.com.model.User
import javax.inject.Inject

class UserPanelViewModel @Inject constructor(private val logoutUser: LogoutUser, private val retrieveUserById: RetrieveUserById) :
    ViewModel() {

    val finishLoading: MutableLiveData<Boolean> = MutableLiveData()
    val logoutSucceed: MutableLiveData<Boolean> = MutableLiveData()
    val getUserSucceed: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null

    lateinit var user: User

    fun logoutUser() {
        logoutUser.execute(
            onComplete = {
                finishLoading.postValue(true)
            },
            onError = {
            },
            onNext = {
                logoutSucceed.postValue(true)

            }, params = Unit
        )
    }

    fun getUserById(idUser: String) {
        retrieveUserById.execute(
            onComplete = {

            },
            onError = {
                errorThrowable = it
                getUserSucceed.postValue(false)
            },
            onNext = {
                user = it
                getUserSucceed.postValue(true)
            }, params = RetrieveUserById.Params.toRetrieve(idUser)
        )
    }

    fun getInstrumentMode(context: Context): String {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(
            ModuleSharedPrefsImpl.CURRENT_INSTRUMENT_MODE,
            ModuleSharedPrefsImpl.INSTRUMENT_MODE_GUITAR
        )
    }
}