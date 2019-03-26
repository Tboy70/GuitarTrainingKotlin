package thomas.guitartrainingkotlin.data.mapper.db

import thomas.guitartrainingkotlin.data.entity.ExerciseEntity
import thomas.guitartrainingkotlin.data.entity.db.ExerciseDBEntity
import thomas.guitartrainingkotlin.data.exception.mapper.DataMappingException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseDBEntityDataMapper @Inject constructor() {

    fun transformFromDB(exerciseDBEntityList: List<ExerciseDBEntity>) =
        exerciseDBEntityList.mapNotNull { exerciseDBEntity ->
            try {
                transformFromDB(exerciseDBEntity)
            } catch (e: DataMappingException) {
                null
            }
        }

    @Throws(DataMappingException::class)
    fun transformFromDB(exerciseDBEntity: ExerciseDBEntity): ExerciseEntity {
        try {
            return ExerciseEntity(
                idProgram = exerciseDBEntity.idProgram,
                idExercise = exerciseDBEntity.idExercise,
                typeExercise = exerciseDBEntity.typeExercise,
                durationExercise = exerciseDBEntity.durationExercise
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToDB(exerciseEntityList: List<ExerciseEntity>) = exerciseEntityList.mapNotNull { exerciseEntity ->
        try {
            transformToDB(exerciseEntity)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformToDB(exerciseEntity: ExerciseEntity): ExerciseDBEntity {
        try {
            return ExerciseDBEntity(
                idProgram = exerciseEntity.idProgram,
                idExercise = exerciseEntity.idExercise,
                typeExercise = exerciseEntity.typeExercise,
                durationExercise = exerciseEntity.durationExercise
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }
}