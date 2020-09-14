package thomas.guitartrainingkotlin.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ScoreDBEntity(
    @PrimaryKey
    val idScore: String = "",
    val valueScore: Float = 0f,
    val dateScore: String = "",
    val idSong: String = ""
)