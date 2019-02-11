package thomas.guitartrainingkotlin.data.entity.remote.score

import com.google.gson.annotations.SerializedName

data class ScoreRemoteEntity(
    @SerializedName("idScore") val idScore: String = "",
    @SerializedName("valueScore") val valueScore: Float = 0f,
    @SerializedName("dateScore") val dateScore: String = "",
    @SerializedName("idSong") val idSong: String = ""
)