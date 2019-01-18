package thomas.example.com.guitarTrainingKotlin.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.user.CreateNewUser
import thomas.example.com.model.User
import javax.inject.Inject

class CreateAccountViewModel @Inject constructor(private val createNewUser: CreateNewUser) : ViewModel() {

    var errorThrowable: Throwable? = null

    val creationSuccess = MutableLiveData<Boolean>()
    val viewState = MutableLiveData<CreateAccountViewState>()
    val errorEvent =
        SingleLiveEvent<CreateAccountErrorEvent>()

    data class CreateAccountViewState(
            var displayingLoading: Boolean = false
    )

    data class CreateAccountErrorEvent(
            val ERROR_TRIGGERED: Boolean = false
    )

    fun createNewUser(pseudoUser: String, emailUser: String, passwordUser: String) {

        viewState.postValue(CreateAccountViewState(true))

        val user = User(null, pseudoUser, emailUser, passwordUser)

        createNewUser.subscribe(
                params = CreateNewUser.Params.toCreate(user),

                onComplete = {
                    creationSuccess.postValue(true)
                    viewState.postValue(CreateAccountViewState(false))
                },

                onError = {
                    errorThrowable = it
                    errorEvent.postValue(CreateAccountErrorEvent(ERROR_TRIGGERED = true))
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        createNewUser.unsubscribe()
    }
}