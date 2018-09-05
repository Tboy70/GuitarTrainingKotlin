package thomas.example.com.guitarTrainingKotlin.viewmodel.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import thomas.example.com.interactor.user.ConnectUser
import thomas.example.com.model.User
import javax.inject.Inject

class LoginHomeViewModel @Inject constructor(private val connectUser: ConnectUser) : ViewModel() {

    val finishLoading: MutableLiveData<Boolean> = MutableLiveData()
    val connectSucceed: MutableLiveData<Boolean> = MutableLiveData()
    val connectFailure: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null

    fun connectUser(username: String, password: String) {

        val user = User()
        user.pseudoUser = username
        user.passwordUser = password

        connectUser.execute(
                onComplete = {
                    finishLoading.postValue(true)
                },
                onError = {
                    errorThrowable = it
                    connectFailure.postValue(true)
                },
                onNext = {
                    connectSucceed.postValue(true)

                }, params = ConnectUser.Params.forLogin(user))
    }
}