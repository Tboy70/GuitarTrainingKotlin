package thomas.guitartrainingkotlin.data.entity.remote.user

import com.google.gson.annotations.SerializedName

data class UserRemoteEntity(
    @SerializedName("userId") val userId: String? = null,
    @SerializedName("userPseudo") val userPseudo: String = "",
    @SerializedName("userEmail") val userEmail: String? = null,
    @SerializedName("userPassword") var userPassword: String = ""
)

