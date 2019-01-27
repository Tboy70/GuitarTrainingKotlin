package thomas.example.com.data.entity

data class SongEntity(
    var idSong: String = "",
    var titleSong: String = "",
    var artistSong: String = "",
    var averageScoreSong: Float = 0.0f,
    var totalScoreSong: Int = 0,
    var nbPlay: Int = 0,
    var lastPlay: String = "",
    var userId: String = "",
    var idInstrument: String = ""
)