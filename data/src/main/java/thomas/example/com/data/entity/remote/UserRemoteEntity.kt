package thomas.example.com.data.entity.remote

import com.google.gson.annotations.SerializedName

data class UserRemoteEntity(@SerializedName("idUser") var idUser: String = "",
                            @SerializedName("pseudoUser") var pseudoUser: String = "",
                            @SerializedName("emailUser") var emailUser: String = "",
                            @SerializedName("passwordUser") var passwordUser: String = "")

