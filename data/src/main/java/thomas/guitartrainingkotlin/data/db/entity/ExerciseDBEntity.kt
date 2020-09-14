package thomas.guitartrainingkotlin.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ExerciseDBEntity(
    @PrimaryKey
    val idExercise: String = "",
    val durationExercise: Int = 0,
    val idProgram: String = "",
    val typeExercise: Int = 0
)