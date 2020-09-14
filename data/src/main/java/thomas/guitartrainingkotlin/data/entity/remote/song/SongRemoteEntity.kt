package thomas.guitartrainingkotlin.data.entity.remote.song

import com.google.gson.annotations.SerializedName
import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

data class SongRemoteEntity(
    @SerializedName("idSong") val idSong: String = "",
    @SerializedName("titleSong") val titleSong: String = "",
    @SerializedName("artistSong") val artistSong: String = "",
    @SerializedName("averageScoreSong") val averageScoreSong: Float = 0.0f,
    @SerializedName("totalScoreSong") val totalScoreSong: Int = 0,
    @SerializedName("nbPlay") val nbPlay: Int = 0,
    @SerializedName("lastPlay") val lastPlay: String = "",
    @SerializedName("userId") var userId: String = "",
    @SerializedName("idInstrument") val idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
)