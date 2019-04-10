package thomas.guitartrainingkotlin.presentation.view.datawrapper

import thomas.guitartrainingkotlin.domain.model.Exercise

class ExerciseViewDataWrapper(private val exercise: Exercise) {

    fun getTypeExercise() = exercise.typeExercise
    fun getDurationExercise() = exercise.durationExercise
}