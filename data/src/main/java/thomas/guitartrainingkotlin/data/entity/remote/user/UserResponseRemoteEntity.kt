package thomas.guitartrainingkotlin.data.entity.remote.user

import com.google.gson.annotations.SerializedName

class UserResponseRemoteEntity {

    @SerializedName("createdId")
    private var createdId: String? = null

    fun getCreatedId(): String? {
        return createdId
    }
}