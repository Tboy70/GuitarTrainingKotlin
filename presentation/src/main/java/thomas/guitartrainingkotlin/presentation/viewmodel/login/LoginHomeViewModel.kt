package thomas.guitartrainingkotlin.presentation.viewmodel.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.user.ConnectUser
import thomas.guitartrainingkotlin.domain.model.User
import thomas.guitartrainingkotlin.presentation.view.state.user.LoginFragmentViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel

@ExperimentalCoroutinesApi
class LoginHomeViewModel @ViewModelInject constructor(
    private val connectUser: ConnectUser
) : StateViewModel<LoginFragmentViewState>() {

    override val currentViewState = LoginFragmentViewState()

    private var userPseudo: String = ""
    private var userPassword: String = ""

    val retrievedUserLiveData = MutableLiveData<String?>()
    val savedUserPseudoLiveData = MutableLiveData<String>()
    val savedUserPasswordLiveData = MutableLiveData<String>()

    fun connectUser(userPseudo: String, userPassword: String) {

        viewState.update {
            loading = true
        }

        val user = User(
            userPseudo = userPseudo,
            userPassword = userPassword
        )

        viewModelScope.launch {
            try {
                connectUser.connectUser(user)
                    .onStart { viewState.update { loading = true } }
                    .onCompletion { viewState.update { loading = false } }
                    .collect { retrievedUserLiveData.postValue(it.userId) }
            } catch (e: Exception) {
                errorLiveEvent.postValue(e)
            }
        }
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