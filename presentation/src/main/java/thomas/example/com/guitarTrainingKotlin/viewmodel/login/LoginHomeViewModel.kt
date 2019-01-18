package thomas.example.com.guitarTrainingKotlin.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.user.ConnectUser
import thomas.example.com.model.User
import javax.inject.Inject

class LoginHomeViewModel @Inject constructor(private val connectUser: ConnectUser) : ViewModel() {

    var errorThrowable: Throwable? = null

    /** ViewData, ViewState and ErrorState **/
    val retrievedUser = MutableLiveData<User>()
    val viewState = MutableLiveData<LoginHomeViewState>()
    val errorEvent = SingleLiveEvent<LoginHomeErrorEvent>()
    /** CAREFUL : SingleLiveEvent cause we don't want it to be kept when rotate for example. **/

    /** For ViewSate **/
    data class LoginHomeViewState(
            var displayingLoading: Boolean = false
    )

    /** For ErrorState **/
    data class LoginHomeErrorEvent(
            val ERROR_TRIGGERED: Boolean = false
    )

    fun connectUser(pseudoUser: String, password: String) {

        viewState.postValue(LoginHomeViewState(true))

        val user = User(null, pseudoUser, null, password)

        connectUser.subscribe(
                params = ConnectUser.Params.forLogin(user),
                onSuccess = {
                    retrievedUser.postValue(it)
                    viewState.postValue(LoginHomeViewState(false))
                },
                onError = {
                    errorThrowable = it
                    errorEvent.postValue(LoginHomeErrorEvent(ERROR_TRIGGERED = true))
                    viewState.postValue(LoginHomeViewState(false))
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        connectUser.unsubscribe()
    }
}