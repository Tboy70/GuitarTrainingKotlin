package thomas.guitartrainingkotlin.domain.model

import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

data class Song(
    var idSong: String = "",
    var titleSong: String = "",
    var artistSong: String = "",
    val averageScoreSong: Float = 0.0f,
    val totalScoreSong: Int = 0,
    val nbPlay: Int = 0,
    val lastPlay: String = "",
    var userId: String = "",
    var idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
)