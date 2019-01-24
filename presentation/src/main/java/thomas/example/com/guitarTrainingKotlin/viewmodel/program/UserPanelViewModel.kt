package thomas.example.com.guitarTrainingKotlin.viewmodel.program

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import thomas.example.com.data.manager.SharedPrefsManagerImpl
import thomas.example.com.guitarTrainingKotlin.view.datawrapper.UserViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.view.state.UserPanelActivityViewState
import thomas.example.com.guitarTrainingKotlin.viewmodel.base.StateViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.user.LogoutUser
import thomas.example.com.interactor.user.RetrieveUserById
import javax.inject.Inject

class UserPanelViewModel @Inject constructor(
    private val logoutUser: LogoutUser,
    private val retrieveUserById: RetrieveUserById
) : StateViewModel<UserPanelActivityViewState>() {

    override val currentViewState = UserPanelActivityViewState()

    val logoutSucceed = MutableLiveData<Boolean>()
    val userRetrievedLiveEvent = SingleLiveEvent<UserViewDataWrapper>()

    var errorThrowable: Throwable? = null

    fun getUserById(userId: String) {
        retrieveUserById.subscribe(
            params = RetrieveUserById.Params.toRetrieve(userId),
            onError = {
                errorLiveEvent.postValue(it)
            },
            onSuccess = {
                userRetrievedLiveEvent.postValue(UserViewDataWrapper(it))
            }
        )
    }

    fun logoutUser() {
//        viewState.postValue(UserPanelViewState(true))
        logoutUser.subscribe(
            onComplete = {
                logoutSucceed.postValue(true)
//                    viewState.postValue(UserPanelViewState(false))
            },
            onError = {
                errorLiveEvent.postValue(it)
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