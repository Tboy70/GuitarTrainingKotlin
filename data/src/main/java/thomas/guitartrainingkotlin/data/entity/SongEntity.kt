package thomas.guitartrainingkotlin.data.entity

import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

data class SongEntity(
    val idSong: String = "",
    val titleSong: String = "",
    val artistSong: String = "",
    val averageScoreSong: Float = 0.0f,
    val totalScoreSong: Int = 0,
    val nbPlay: Int = 0,
    val lastPlay: String = "",
    val userId: String = "",
    val idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
)