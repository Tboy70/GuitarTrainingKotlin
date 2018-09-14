package thomas.example.com.guitarTrainingKotlin.viewmodel.login

import android.arch.lifecycle.ViewModel
import thomas.example.com.interactor.user.CreateNewUser
import thomas.example.com.model.User
import javax.inject.Inject

class CreateAccountViewModel @Inject constructor(private val createNewUser: CreateNewUser) : ViewModel() {

    fun createNewUser(pseudoUser: String, emailUser: String, passwordUser: String) {
        val user = User()
        user.pseudoUser = pseudoUser
        user.emailUser = emailUser
        user.passwordUser = passwordUser

        createNewUser.execute(
                onComplete = {

                },
                onError = {

                },
                onNext = {


                }, params = CreateNewUser.Params.toCreate(user))
    }

}