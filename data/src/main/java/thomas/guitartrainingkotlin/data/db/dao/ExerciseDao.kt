package thomas.guitartrainingkotlin.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import thomas.guitartrainingkotlin.data.db.entity.ExerciseDBEntity

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExerciseList(exerciseDBEntityList: List<ExerciseDBEntity>)

    @Query("DELETE FROM ExerciseDBEntity")
    fun clearExercise()
}