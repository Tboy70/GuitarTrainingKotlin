package thomas.example.com.data.entity

data class UserEntity(
    val userId: String? = null,
    val userPseudo: String = "",
    val userEmail: String? = null,
    val userPassword: String = ""
)