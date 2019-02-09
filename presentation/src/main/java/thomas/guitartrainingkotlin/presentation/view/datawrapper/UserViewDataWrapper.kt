package thomas.guitartrainingkotlin.presentation.view.datawrapper

import thomas.guitartrainingkotlin.domain.model.User

class UserViewDataWrapper(
    private val user: User
) {
    fun getUserPseudo() = user.userPseudo

    fun getUserEmail() = user.userEmail
}