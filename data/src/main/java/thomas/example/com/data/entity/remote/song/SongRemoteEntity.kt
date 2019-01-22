package thomas.example.com.data.entity.remote.song

import com.google.gson.annotations.SerializedName

data class SongRemoteEntity(
    @SerializedName("idSong") var idSong: String = "",
    @SerializedName("titleSong") var titleSong: String = "",
    @SerializedName("artistSong") var artistSong: String = "",
    @SerializedName("averageScoreSong") var averageScoreSong: Float = 0.0f,
    @SerializedName("totalScoreSong") var totalScoreSong: Int = 0,
    @SerializedName("nbPlay") var nbPlay: Int = 0,
    @SerializedName("lastPlay") var lastPlay: String = "",
    @SerializedName("userId") var idUser: String = "",
    @SerializedName("idInstrument") var idInstrument: String = ""
)