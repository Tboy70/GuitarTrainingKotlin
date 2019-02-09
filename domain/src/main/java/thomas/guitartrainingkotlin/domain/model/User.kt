package thomas.guitartrainingkotlin.domain.model

data class User(
    val userId: String? = null,
    val userPseudo: String = "",
    val userEmail: String? = null,
    val userPassword: String = ""
)