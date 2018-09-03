package thomas.example.com.data.entity.remote.program

import com.google.gson.annotations.SerializedName

class ProgramResponseRemoteEntity {

    @SerializedName("createdId")
    private var createdId: String? = null

    fun getCreatedId(): String? {
        return createdId
    }

    fun setCreatedId(createdId: String) {
        this.createdId = createdId
    }
}