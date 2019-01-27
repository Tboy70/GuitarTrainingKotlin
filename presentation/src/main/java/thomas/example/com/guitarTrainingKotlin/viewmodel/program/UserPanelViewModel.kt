package thomas.example.com.guitarTrainingKotlin.viewmodel.program

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import thomas.example.com.data.manager.SharedPrefsManagerImpl
import thomas.example.com.guitarTrainingKotlin.view.datawrapper.UserViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.view.state.UserPanelActivityViewState
import thomas.example.com.guitarTrainingKotlin.viewmodel.base.AndroidStateViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.user.LogoutUser
import thomas.example.com.interactor.user.RetrieveUserById
import javax.inject.Inject

class UserPanelViewModel @Inject constructor(
    application: Application,
    /** On n'injecte pas de context directement,on préfère injecter l'objet Application. **/
    private val logoutUser: LogoutUser,
    private val retrieveUserById: RetrieveUserById
) : AndroidStateViewModel<UserPanelActivityViewState>(application) {

    override val currentViewState = UserPanelActivityViewState()

    private var userId: String? = null
    // TODO : Refactor --> Why string and int !?
    private var instrumentMode: String? = SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR

    val logoutSucceedLiveEvent = SingleLiveEvent<Boolean>()
    val userNotRetrievedLiveEvent = SingleLiveEvent<Boolean>()
    val userRetrievedLiveEvent = SingleLiveEvent<UserViewDataWrapper>()

    var errorThrowable: Throwable? = null

    init {
        retrieveInstrumentMode()
    }

    override fun onCleared() {
        super.onCleared()
        logoutUser.unsubscribe()
        retrieveUserById.unsubscribe()
    }

    fun logoutUser() {
        logoutUser.subscribe(
            onComplete = {
                logoutSucceedLiveEvent.postValue(true)
            },
            onError = {
                errorLiveEvent.postValue(it)
            }
        )
    }

    fun getUserId() = userId

    fun retrieveUserId() {
        userId = PreferenceManager.getDefaultSharedPreferences(getApplication())
            .getString(SharedPrefsManagerImpl.CURRENT_USER_ID, "0")

        userId?.let { userId ->
            if (!userId.isEmpty() && userId != "0") {
                getUserById(userId)
            } else {
                userNotRetrievedLiveEvent.postValue(true)
            }
        }
    }

    private fun retrieveInstrumentMode() {
        instrumentMode = PreferenceManager.getDefaultSharedPreferences(getApplication())
            .getString(SharedPrefsManagerImpl.CURRENT_INSTRUMENT_MODE, SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR)
    }

    private fun getUserById(userId: String) {
        retrieveUserById.subscribe(
            params = RetrieveUserById.Params.toRetrieve(userId),
            onSuccess = {
                userRetrievedLiveEvent.postValue(UserViewDataWrapper(it))
            },
            onError = {
                this.userId = null
                errorLiveEvent.postValue(it)
            }
        )
    }
}