package thomas.guitartrainingkotlin.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ExerciseDBEntity(
    @PrimaryKey
    var idExercise: String = "",
    var durationExercise: Int = 0,
    var idProgram: String = "",
    var typeExercise: Int = 0
)