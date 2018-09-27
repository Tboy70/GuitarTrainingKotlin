package thomas.example.com.data.entity

data class SongEntity(var idSong: String = "",
                      var titleSong: String = "",
                      var artistSong: String = "",
                      var scoreSong: Int = 0,
                      var nbPlay: Int = 0,
                      var idUser: String = "")