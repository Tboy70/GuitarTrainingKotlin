package thomas.example.com.guitarTrainingKotlin.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import thomas.example.com.interactor.user.ConnectUser
import thomas.example.com.model.User
import javax.inject.Inject

class LoginHomeViewModel @Inject constructor(private val connectUser: ConnectUser) : ViewModel() {

    val finishLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun connectUser(username: String, password: String) {

        val user = User()
        user.pseudoUser = username
        user.passwordUser = password

        connectUser.execute(
                onComplete = {
                    Log.e("TEST", "onComplete")
                    finishLoading.postValue(true)
                },
                onError = {
                    Log.e("TEST", "onError")
                },
                onNext = {
                    Log.e("TEST", "onNext")
                }, params = ConnectUser.Params.forLogin(user))
    }
}