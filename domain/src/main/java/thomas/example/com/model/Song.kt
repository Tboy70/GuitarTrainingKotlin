package thomas.example.com.model

data class Song(var idSong: String = "",
                var titleSong: String = "",
                var artistSong: String = "",
                var scoreSong: Int = 0,
                var nbPlay: Int = 0,
                var idUser: String = "")