package thomas.guitartrainingkotlin.data.entity.remote.user

import com.google.gson.annotations.SerializedName

data class UserRemoteEntity(
    @SerializedName("userId") val userId: String,
    @SerializedName("userPseudo") val userPseudo: String = "",
    @SerializedName("userEmail") val userEmail: String = "",
    @SerializedName("userPassword") var userPassword: String? = ""   // Password can be null (when user retrieved from API)
)

