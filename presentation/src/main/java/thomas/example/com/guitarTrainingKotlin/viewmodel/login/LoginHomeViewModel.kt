package thomas.example.com.guitarTrainingKotlin.viewmodel.login

import androidx.lifecycle.MutableLiveData
import thomas.example.com.guitarTrainingKotlin.view.LoginFragmentViewState
import thomas.example.com.guitarTrainingKotlin.viewmodel.base.StateViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.user.ConnectUser
import thomas.example.com.model.User
import javax.inject.Inject

class LoginHomeViewModel @Inject constructor(
    private val connectUser: ConnectUser
) : StateViewModel<LoginFragmentViewState>() {

    override val currentViewState = LoginFragmentViewState()

    var errorThrowable: Throwable? = null

    /** ViewData, ViewState and ErrorState **/
    val retrievedUser = MutableLiveData<Boolean>()

    fun connectUser(userPseudo: String, userPassword: String) {

        viewState.update {
            loading = true
        }
//        viewState.postValue(LoginHomeViewState(true))

        val user = User(
            userPseudo = userPseudo,
            userPassword = userPassword
        )

        connectUser.subscribe(
            params = ConnectUser.Params.forLogin(user),
            onSuccess = {
                retrievedUser.postValue(true)
                viewState.update {
                    loading = false
                }
//                viewState.postValue(LoginHomeViewState(false))
            },
            onError = {
                errorLiveEvent.postValue(it)
                viewState.update {
                    loading = false
                }
//                errorThrowable = it
//                errorEvent.postValue(LoginHomeErrorEvent(ERROR_TRIGGERED = true))
//                viewState.postValue(LoginHomeViewState(false))
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        connectUser.unsubscribe()
    }
}