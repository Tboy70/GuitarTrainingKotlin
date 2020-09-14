package thomas.guitartrainingkotlin.presentation.viewmodel.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.user.CreateNewUser
import thomas.guitartrainingkotlin.domain.model.User
import thomas.guitartrainingkotlin.presentation.view.state.user.CreateAccountFragmentViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel

@ExperimentalCoroutinesApi
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
            userPseudo = pseudoUser,
            userEmail = emailUser,
            userPassword = passwordUser
        )

        viewModelScope.launch {
            try {
                createNewUser.createNewUser(user)
                    .onStart { viewState.update { loading = true } }
                    .onCompletion { viewState.update { loading = false } }
                    .collect {
                        userCreationLiveData.postValue(true)
                    }
            } catch (e: Exception) {
                errorLiveEvent.postValue(e)
            }
        }

//        compositeDisposable?.add(
//            createNewUser.subscribe(
//                params = CreateNewUser.Params.toCreate(user),
//                onComplete = {
//                    userCreationLiveData.postValue(true)
//                    viewState.update {
//                        loading = false
//                    }
//                },
//                onError = {
//                    errorLiveEvent.postValue(it)
//                    viewState.update {
//                        loading = false
//                    }
//                }
//            )
//        )
    }
}