package thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper

import thomas.example.com.model.User
import java.io.Serializable

class UserViewDataWrapper(@Transient private val user: User) : Serializable {

    fun getIdUser(): String? {
        return user.userId
    }

    fun getPseudoUser() : String {
        return user.userPseudo
    }

    fun getEmailUser() : String? {
        return user.userEmail
    }
}