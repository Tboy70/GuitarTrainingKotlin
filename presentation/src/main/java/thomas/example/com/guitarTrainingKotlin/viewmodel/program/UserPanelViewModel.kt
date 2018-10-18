package thomas.example.com.guitarTrainingKotlin.viewmodel.program

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.guitarTrainingKotlin.viewmodel.SingleLiveEvent
import thomas.example.com.interactor.user.LogoutUser
import thomas.example.com.interactor.user.RetrieveUserById
import thomas.example.com.model.User
import javax.inject.Inject

class UserPanelViewModel @Inject constructor(
        private val logoutUser: LogoutUser,
        private val retrieveUserById: RetrieveUserById
) : ViewModel() {

    var errorThrowable: Throwable? = null

    val logoutSucceed = MutableLiveData<Boolean>()
    val userRetrieved = MutableLiveData<User>() //TODO : Use ViewDataWrapper
    val viewState = MutableLiveData<UserPanelViewState>()
    val errorEvent = SingleLiveEvent<UserPanelErrorEvent>()

    data class UserPanelViewState(
            var displayingLoading: Boolean = false
    )

    data class UserPanelErrorEvent(
            val ERROR_TRIGGERED: Boolean = false
    )

    fun logoutUser() {
        viewState.postValue(UserPanelViewState(true))
        logoutUser.subscribe(
                onComplete = {
                    logoutSucceed.postValue(true)
                    viewState.postValue(UserPanelViewState(false))
                },
                onError = {
                    errorThrowable = it
                    logoutSucceed.postValue(true)
                    errorEvent.postValue(UserPanelErrorEvent(ERROR_TRIGGERED = true))
                }
        )
    }

    fun getUserById(idUser: String) {
        retrieveUserById.subscribe(
                params = RetrieveUserById.Params.toRetrieve(idUser),
                onError = {
                    errorThrowable = it
                },
                onSuccess = {   // TODO : Use ViewDataWrapper instead
                    userRetrieved.postValue(it)
                }
        )
    }

    fun getInstrumentMode(context: Context): String {
        // TODO : Use a use case too ?
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(
                ModuleSharedPrefsImpl.CURRENT_INSTRUMENT_MODE,
                ModuleSharedPrefsImpl.INSTRUMENT_MODE_GUITAR
        )
    }

    override fun onCleared() {
        super.onCleared()
        logoutUser.unsubscribe()
        retrieveUserById.unsubscribe()
    }
}