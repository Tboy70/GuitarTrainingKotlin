package thomas.example.com.data.mapper

import thomas.example.com.data.entity.ExerciseEntity
import thomas.example.com.model.Exercise
import javax.inject.Singleton

@Singleton
class ExerciseEntityDataMapper {

    fun transformExerciseEntityToExerciseModel(exerciseEntity: ExerciseEntity): Exercise {
        val exercise = Exercise()
        exercise.idExercise = exerciseEntity.idExercise
        exercise.durationExercise = exerciseEntity.durationExercise
        exercise.idProgram = exerciseEntity.idProgram
        exercise.typeExercise = exerciseEntity.typeExercise
        return exercise
    }

    fun transformListExerciseEntitiesToExerciseModels(exerciseEntityList: List<ExerciseEntity>): List<Exercise> {
        val exerciseList: MutableList<Exercise> = mutableListOf()

        for (exerciseEntity: ExerciseEntity in exerciseEntityList) {
            exerciseList.add(transformExerciseEntityToExerciseModel(exerciseEntity))
        }
        return exerciseList
    }

    fun transformExerciseModelToExerciseEntity(exercise: Exercise): ExerciseEntity {
        val exerciseEntity = ExerciseEntity()
        exerciseEntity.idExercise = exercise.idExercise
        exerciseEntity.durationExercise = exercise.durationExercise
        exerciseEntity.idProgram = exercise.idProgram
        exerciseEntity.typeExercise = exercise.typeExercise
        return exerciseEntity
    }

    fun transformListExerciseModelsToExerciseEntites(exerciseModelList: List<Exercise>): List<ExerciseEntity> {
        val exerciseEntityList: MutableList<ExerciseEntity> = mutableListOf()

        for (exerciseModel: Exercise in exerciseModelList) {
            exerciseEntityList.add(transformExerciseModelToExerciseEntity(exerciseModel))
        }
        return exerciseEntityList
    }
}