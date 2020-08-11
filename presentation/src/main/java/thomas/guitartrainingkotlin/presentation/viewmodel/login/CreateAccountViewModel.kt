package thomas.guitartrainingkotlin.presentation.viewmodel.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.domain.interactor.user.CreateNewUser
import thomas.guitartrainingkotlin.domain.model.User
import thomas.guitartrainingkotlin.presentation.view.state.user.CreateAccountFragmentViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import javax.inject.Inject

class CreateAccountViewModel @ViewModelInject constructor(
    private val createNewUser: CreateNewUser
) : StateViewModel<CreateAccountFragmentViewState>() {

    override val currentViewState = CreateAccountFragmentViewState()

    val userCreationLiveData = MutableLiveData<Boolean>()

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

        compositeDisposable?.add(
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
        )
    }
}