package thomas.guitartrainingkotlin.data.entity

data class UserEntity(
    val userId: String,
    val userPseudo: String = "",
    val userEmail: String = "",
    val userPassword: String? = ""   // Password can be null (when user retrieved from API)
)