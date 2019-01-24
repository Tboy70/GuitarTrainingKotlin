package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.ExerciseEntity
import thomas.example.com.data.entity.remote.exercise.ExerciseRemoteEntity
import thomas.example.com.data.exception.mapper.DataMappingException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRemoteEntityDataMapper @Inject constructor() {

    @Throws(DataMappingException::class)
    fun transformFromEntity(exerciseEntity: ExerciseEntity): ExerciseRemoteEntity {
        try {
            return ExerciseRemoteEntity(
                idExercise = exerciseEntity.idExercise,
                durationExercise = exerciseEntity.durationExercise,
                idProgram = exerciseEntity.idProgram,
                typeExercise = exerciseEntity.typeExercise
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformFromEntity(exerciseEntityList: List<ExerciseEntity>) = exerciseEntityList.mapNotNull { exerciseEntity ->
        try {
            transformFromEntity(exerciseEntity)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformToEntity(exerciseRemoteEntity: ExerciseRemoteEntity): ExerciseEntity {
        try {
            return ExerciseEntity(
                idExercise = exerciseRemoteEntity.idExercise,
                durationExercise = exerciseRemoteEntity.durationExercise,
                idProgram = exerciseRemoteEntity.idProgram,
                typeExercise = exerciseRemoteEntity.typeExercise
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToEntity(exerciseRemoteEntityList: List<ExerciseRemoteEntity>) = exerciseRemoteEntityList.mapNotNull { exerciseRemoteEntity ->
        try {
            transformToEntity(exerciseRemoteEntity)
        } catch (e: DataMappingException) {
            null
        }
    }
}