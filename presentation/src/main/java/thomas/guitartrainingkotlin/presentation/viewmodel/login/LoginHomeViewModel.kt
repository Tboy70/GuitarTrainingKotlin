package thomas.guitartrainingkotlin.presentation.viewmodel.login

import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.domain.interactor.user.ConnectUser
import thomas.guitartrainingkotlin.domain.model.User
import thomas.guitartrainingkotlin.presentation.view.state.user.LoginFragmentViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import javax.inject.Inject

class LoginHomeViewModel @Inject constructor(
    private val connectUser: ConnectUser
) : StateViewModel<LoginFragmentViewState>() {

    override val currentViewState = LoginFragmentViewState()

    val retrievedUserLiveData = MutableLiveData<Boolean>()
    val savedUserPseudoLiveData = MutableLiveData<String>()
    val savedUserPasswordLiveData = MutableLiveData<String>()

    private var userPseudo: String = ""
    private var userPassword: String = ""

    override fun onCleared() {
        super.onCleared()
        connectUser.unsubscribe()
    }

    fun connectUser(userPseudo: String, userPassword: String) {

        viewState.update {
            loading = true
        }

        val user = User(
            userPseudo = userPseudo,
            userPassword = userPassword
        )

        connectUser.subscribe(
            params = ConnectUser.Params.forLogin(user),
            onSuccess = {
                retrievedUserLiveData.postValue(true)
                viewState.update {
                    loading = false
                }
            },
            onError = {
                errorLiveEvent.postValue(it)
                viewState.update {
                    loading = false
                }
            }
        )
    }

    fun saveUserPseudoValue(userPseudo: String) {
        this.userPseudo = userPseudo
    }

    fun saveUserPasswordValue(userPassword: String) {
        this.userPassword = userPassword
    }

    fun retrieveSavedValues() {
        savedUserPseudoLiveData.postValue(userPseudo)
        savedUserPasswordLiveData.postValue(userPassword)
    }
}