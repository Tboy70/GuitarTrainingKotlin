package thomas.example.com.guitarTrainingKotlin.view.datawrapper

import thomas.example.com.model.User

class UserViewDataWrapper(
    private val user: User
) {
    fun getUserPseudo() = user.userPseudo

    fun getUserEmail() = user.userEmail
}