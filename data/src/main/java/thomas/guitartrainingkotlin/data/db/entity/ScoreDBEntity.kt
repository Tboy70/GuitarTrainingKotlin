package thomas.guitartrainingkotlin.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ScoreDBEntity(
    @PrimaryKey
    var idScore: String = "",
    var valueScore: Float = 0f,
    var dateScore: String = "",
    var idSong: String = ""
)