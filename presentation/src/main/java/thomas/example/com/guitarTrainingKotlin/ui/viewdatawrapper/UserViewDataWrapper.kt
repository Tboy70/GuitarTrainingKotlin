package thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper

import thomas.example.com.model.User
import java.io.Serializable

class UserViewDataWrapper(@Transient private val user: User) : Serializable {

    fun getIdUser(): String? {
        return user.idUser
    }

    fun getPseudoUser() : String {
        return user.pseudoUser
    }

    fun getEmailUser() : String? {
        return user.emailUser
    }
}