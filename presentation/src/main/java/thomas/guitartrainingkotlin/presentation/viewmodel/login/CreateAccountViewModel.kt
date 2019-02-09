package thomas.guitartrainingkotlin.presentation.viewmodel.login

import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.presentation.view.state.user.CreateAccountFragmentViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.domain.interactor.user.CreateNewUser
import thomas.guitartrainingkotlin.domain.model.User
import javax.inject.Inject

class CreateAccountViewModel @Inject constructor(
    private val createNewUser: CreateNewUser
) : StateViewModel<CreateAccountFragmentViewState>() {

    override val currentViewState =
        CreateAccountFragmentViewState()

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