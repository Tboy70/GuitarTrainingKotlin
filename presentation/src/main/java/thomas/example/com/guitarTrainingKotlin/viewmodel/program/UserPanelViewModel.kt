package thomas.example.com.guitarTrainingKotlin.viewmodel.program

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.data.manager.SharedPrefsManagerImpl
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.UserViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.user.LogoutUser
import thomas.example.com.interactor.user.RetrieveUserById
import javax.inject.Inject

class UserPanelViewModel @Inject constructor(
        private val logoutUser: LogoutUser,
        private val retrieveUserById: RetrieveUserById
) : ViewModel() {

    val logoutSucceed = MutableLiveData<Boolean>()
    val userRetrieved = MutableLiveData<UserViewDataWrapper>()
    val viewState = MutableLiveData<UserPanelViewState>()
    val errorEvent = SingleLiveEvent<UserPanelErrorEvent>()

    var errorThrowable: Throwable? = null

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
                    errorEvent.postValue(UserPanelErrorEvent(ERROR_TRIGGERED = true))
                },
                onSuccess = {
                    userRetrieved.postValue(UserViewDataWrapper(it))
                }
        )
    }

    fun getInstrumentMode(context: Context): String {
        // TODO : Use a use case too ?
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(
                SharedPrefsManagerImpl.CURRENT_INSTRUMENT_MODE,
                SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR
        )
    }

    override fun onCleared() {
        super.onCleared()
        logoutUser.unsubscribe()
        retrieveUserById.unsubscribe()
    }
}