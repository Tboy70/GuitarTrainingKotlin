package thomas.example.com.data.entity.remote.song

import com.google.gson.annotations.SerializedName

data class SongRemoteEntity(@SerializedName("idSong") var idSong: String = "",
                            @SerializedName("titleSong") var titleSong: String = "",
                            @SerializedName("artistSong") var artistSong: String = "",
                            @SerializedName("scoreSong") var scoreSong: Int = 0,
                            @SerializedName("nbPlay") var nbPlay: Int = 0,
                            @SerializedName("idUser") var idUser: String = "")