package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.ExerciseEntity
import thomas.example.com.data.entity.remote.ExerciseRemoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRemoteEntityDataMapper @Inject constructor() {

    fun transformRemoteEntityToEntity(exerciseRemoteEntity: ExerciseRemoteEntity): ExerciseEntity {
        val exerciseEntity = ExerciseEntity()
        exerciseEntity.idExercise = exerciseRemoteEntity.idExercise
        exerciseEntity.durationExercise = exerciseRemoteEntity.durationExercise
        exerciseEntity.idProgram = exerciseRemoteEntity.idProgram
        exerciseEntity.typeExercise = exerciseRemoteEntity.typeExercise

        return exerciseEntity
    }

    fun transformListRemoteEntitiesToListEntities(exerciseRemoteEntitiesList: List<ExerciseRemoteEntity>): MutableList<ExerciseEntity> {
        val exerciseEntitiesList: MutableList<ExerciseEntity> = mutableListOf()

        for (exerciseRemoteEntity: ExerciseRemoteEntity in exerciseRemoteEntitiesList) {
            exerciseEntitiesList.add(transformRemoteEntityToEntity(exerciseRemoteEntity))
        }
        return exerciseEntitiesList
    }

    fun transformEntityToRemoteEntity(exerciseEntity: ExerciseEntity): ExerciseRemoteEntity {
        val exerciseRemoteEntity = ExerciseRemoteEntity()
        exerciseRemoteEntity.idExercise = exerciseEntity.idExercise
        exerciseRemoteEntity.durationExercise = exerciseEntity.durationExercise
        exerciseRemoteEntity.idProgram = exerciseEntity.idProgram
        exerciseRemoteEntity.typeExercise = exerciseEntity.typeExercise

        return exerciseRemoteEntity
    }

    fun transformListEntitiesToListRemoteEntities(exerciseEntitiesList: List<ExerciseEntity>): MutableList<ExerciseRemoteEntity> {
        val exerciseRemoteEntitiesList: MutableList<ExerciseRemoteEntity> = mutableListOf()

        for (exerciseEntity: ExerciseEntity in exerciseEntitiesList) {
            exerciseRemoteEntitiesList.add(transformEntityToRemoteEntity(exerciseEntity))
        }
        return exerciseRemoteEntitiesList
    }
}