package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.ExerciseEntity
import thomas.example.com.data.entity.remote.ExerciseRemoteEntity
import javax.inject.Singleton

@Singleton
class ExerciseRemoteEntityDataMapper {

    fun transformExerciseRemoteEntityToExerciseEntity(exerciseRemoteEntity: ExerciseRemoteEntity): ExerciseEntity {
        val exerciseEntity = ExerciseEntity()
        exerciseEntity.idExercise = exerciseRemoteEntity.idExercise
        exerciseEntity.durationExercise = exerciseRemoteEntity.durationExercise
        exerciseEntity.idProgram = exerciseRemoteEntity.idProgram
        exerciseEntity.typeExercise = exerciseRemoteEntity.typeExercise

        return exerciseEntity
    }

    fun transformListExerciseRemoteEntitiesToListExerciseEntities(exerciseRemoteEntitiesList: List<ExerciseRemoteEntity>): List<ExerciseEntity> {
        val exerciseEntitiesList: MutableList<ExerciseEntity> = mutableListOf()

        for (exerciseRemoteEntity: ExerciseRemoteEntity in exerciseRemoteEntitiesList) {
            exerciseEntitiesList.add(transformExerciseRemoteEntityToExerciseEntity(exerciseRemoteEntity))
        }
        return exerciseEntitiesList
    }

    fun transformExerciseEntityToExerciseRemoteEntity(exerciseEntity: ExerciseEntity): ExerciseRemoteEntity {
        val exerciseRemoteEntity = ExerciseRemoteEntity()
        exerciseRemoteEntity.idExercise = exerciseEntity.idExercise
        exerciseRemoteEntity.durationExercise = exerciseEntity.durationExercise
        exerciseRemoteEntity.idProgram = exerciseEntity.idProgram
        exerciseRemoteEntity.typeExercise = exerciseEntity.typeExercise

        return exerciseRemoteEntity
    }

    fun transformListExerciseEntitiesToListExerciseRemoteEntities(exerciseEntitiesList: List<ExerciseEntity>): List<ExerciseRemoteEntity> {
        val exerciseRemoteEntitiesList: MutableList<ExerciseRemoteEntity> = mutableListOf()

        for (exerciseEntity: ExerciseEntity in exerciseEntitiesList) {
            exerciseRemoteEntitiesList.add(transformExerciseEntityToExerciseRemoteEntity(exerciseEntity))
        }
        return exerciseRemoteEntitiesList
    }
}