package thomas.example.com.guitarTrainingKotlin.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.interactor.user.CreateNewUser
import thomas.example.com.model.User
import javax.inject.Inject

class CreateAccountViewModel @Inject constructor(private val createNewUser: CreateNewUser) : ViewModel() {

    val finishLoading: MutableLiveData<Boolean> = MutableLiveData()
    val creationSuccess: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null

    fun createNewUser(pseudoUser: String, emailUser: String, passwordUser: String) {
        val user = User(null, pseudoUser, emailUser, passwordUser)

        createNewUser.execute(
            onComplete = {
                finishLoading.postValue(true)
            },
            onError = {
                errorThrowable = it
                creationSuccess.postValue(false)
            },
            onNext = {
                creationSuccess.postValue(true)
            }, params = CreateNewUser.Params.toCreate(user)
        )
    }

}