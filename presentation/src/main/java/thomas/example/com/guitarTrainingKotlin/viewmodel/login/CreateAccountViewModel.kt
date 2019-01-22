package thomas.example.com.guitarTrainingKotlin.viewmodel.login

import androidx.lifecycle.MutableLiveData
import thomas.example.com.guitarTrainingKotlin.view.state.CreateAccountFragmentViewState
import thomas.example.com.guitarTrainingKotlin.viewmodel.base.StateViewModel
import thomas.example.com.interactor.user.CreateNewUser
import thomas.example.com.model.User
import javax.inject.Inject

class CreateAccountViewModel @Inject constructor(
    private val createNewUser: CreateNewUser
) : StateViewModel<CreateAccountFragmentViewState>() {

    override val currentViewState = CreateAccountFragmentViewState()

    val userCreationLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        createNewUser.unsubscribe()
    }

    fun createNewUser(pseudoUser: String, emailUser: String, passwordUser: String) {

        viewState.update {
            loading = true
        }

        val user = User(
            userId = null,
            userPseudo = pseudoUser,
            userEmail = emailUser,
            userPassword = passwordUser
        )

        createNewUser.subscribe(
            params = CreateNewUser.Params.toCreate(user),
            onComplete = {
                userCreationLiveData.postValue(true)
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
}