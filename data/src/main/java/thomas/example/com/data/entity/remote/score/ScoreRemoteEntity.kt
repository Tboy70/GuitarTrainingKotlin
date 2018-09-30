package thomas.example.com.data.entity.remote.score

import com.google.gson.annotations.SerializedName

data class ScoreRemoteEntity(@SerializedName("idScore") var idScore: String = "",
                             @SerializedName("valueScore") var valueScore: Int = 0,
                             @SerializedName("dateScore") var dateScore: String = "",
                             @SerializedName("idSong") var idSong: String = "")