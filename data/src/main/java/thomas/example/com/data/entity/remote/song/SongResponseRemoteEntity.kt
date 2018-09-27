package thomas.example.com.data.entity.remote.song

import com.google.gson.annotations.SerializedName

class SongResponseRemoteEntity {

    @SerializedName("createdId")
    private var createdId: String? = null

    fun getCreatedId(): String? {
        return createdId
    }

}