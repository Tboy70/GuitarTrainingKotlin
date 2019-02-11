package thomas.guitartrainingkotlin.domain.model

data class Exercise(
    val idExercise: String = "",
    val durationExercise: Int = 0,
    var idProgram: String = "",
    val typeExercise: Int = 0
)