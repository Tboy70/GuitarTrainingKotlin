package thomas.example.com.data.entity.remote.user

import com.google.gson.annotations.SerializedName

data class UserRemoteEntity(
    @SerializedName("idUser") val idUser: String?,
    @SerializedName("pseudoUser") val pseudoUser: String,
    @SerializedName("emailUser") val emailUser: String?,
    @SerializedName("passwordUser") var passwordUser: String?
)

