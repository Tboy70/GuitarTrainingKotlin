package thomas.guitartrainingkotlin.data.entity.remote.program

import com.google.gson.annotations.SerializedName

class ProgramResponseRemoteEntity {

    @SerializedName("createdId")
    private var createdId: String = ""

    fun getCreatedId(): String {
        return createdId
    }
}