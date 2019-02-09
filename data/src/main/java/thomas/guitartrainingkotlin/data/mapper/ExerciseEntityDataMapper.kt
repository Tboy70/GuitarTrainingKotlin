package thomas.guitartrainingkotlin.data.mapper

import thomas.guitartrainingkotlin.data.entity.ExerciseEntity
import thomas.guitartrainingkotlin.data.exception.mapper.DataMappingException
import thomas.guitartrainingkotlin.domain.model.Exercise
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseEntityDataMapper @Inject constructor() {

    fun transformFromEntity(exerciseEntityList: List<ExerciseEntity>) = exerciseEntityList.mapNotNull { exerciseEntity ->
        try {
            transformFromEntity(exerciseEntity)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformFromEntity(exerciseEntity: ExerciseEntity): Exercise {
        try {
            return Exercise(
                idExercise = exerciseEntity.idExercise,
                durationExercise = exerciseEntity.durationExercise,
                idProgram = exerciseEntity.idProgram,
                typeExercise = exerciseEntity.typeExercise
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToEntity(exerciseList: List<Exercise>) = exerciseList.mapNotNull { exercise ->
        try {
            transformToEntity(exercise)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformToEntity(exercise: Exercise): ExerciseEntity {
        try {
            return ExerciseEntity(
                idExercise = exercise.idExercise,
                durationExercise = exercise.durationExercise,
                idProgram = exercise.idProgram,
                typeExercise = exercise.typeExercise
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }
}