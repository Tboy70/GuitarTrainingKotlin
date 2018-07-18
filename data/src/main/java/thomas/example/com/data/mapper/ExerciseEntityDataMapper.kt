package thomas.example.com.data.mapper

import thomas.example.com.data.entity.ExerciseEntity
import thomas.example.com.model.Exercise
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseEntityDataMapper @Inject constructor() {

    fun transformEntityToModel(exerciseEntity: ExerciseEntity): Exercise {
        val exercise = Exercise()
        exercise.idExercise = exerciseEntity.idExercise
        exercise.durationExercise = exerciseEntity.durationExercise
        exercise.idProgram = exerciseEntity.idProgram
        exercise.typeExercise = exerciseEntity.typeExercise
        return exercise
    }

    fun transformListEntitiesToListModels(exerciseEntityList: List<ExerciseEntity>): List<Exercise> {
        val exerciseList: MutableList<Exercise> = mutableListOf()

        for (exerciseEntity: ExerciseEntity in exerciseEntityList) {
            exerciseList.add(transformEntityToModel(exerciseEntity))
        }
        return exerciseList
    }

    fun transformModelToEntity(exercise: Exercise): ExerciseEntity {
        val exerciseEntity = ExerciseEntity()
        exerciseEntity.idExercise = exercise.idExercise
        exerciseEntity.durationExercise = exercise.durationExercise
        exerciseEntity.idProgram = exercise.idProgram
        exerciseEntity.typeExercise = exercise.typeExercise
        return exerciseEntity
    }

    fun transformListModelsToListEntities(exerciseModelList: List<Exercise>): List<ExerciseEntity> {
        val exerciseEntityList: MutableList<ExerciseEntity> = mutableListOf()

        for (exerciseModel: Exercise in exerciseModelList) {
            exerciseEntityList.add(transformModelToEntity(exerciseModel))
        }
        return exerciseEntityList
    }
}