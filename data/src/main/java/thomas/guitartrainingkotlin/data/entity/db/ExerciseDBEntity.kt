package thomas.guitartrainingkotlin.data.entity.db

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import thomas.guitartrainingkotlin.data.manager.db.DBManagerImpl

@Table(database = DBManagerImpl::class)
data class ExerciseDBEntity(

    @PrimaryKey
    var idExercise: String = "",

    @Column
    var durationExercise: Int = 0,

    @Column
    var idProgram: String = "",

    @Column
    var typeExercise: Int = 0

) {

    override fun toString(): String {
        return "ExerciseDBEntity(idExercise='$idExercise', durationExercise=$durationExercise, idProgram='$idProgram', typeExercise=$typeExercise)"
    }
}