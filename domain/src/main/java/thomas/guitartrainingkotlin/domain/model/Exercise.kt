package thomas.guitartrainingkotlin.domain.model

data class Exercise(
    val idExercise: String = "",
    var durationExercise: Int = 0,
    var idProgram: String = "",
    var typeExercise: Int = 0
)