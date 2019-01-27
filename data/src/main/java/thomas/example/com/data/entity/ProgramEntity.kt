package thomas.example.com.data.entity

data class ProgramEntity(
    var idProgram: String = "",
    var nameProgram: String = "",
    var descriptionProgram: String = "",
    var defaultProgram: Boolean = false,
    var userId: String? = "",
    var exerciseEntityList: List<ExerciseEntity> = emptyList(),
    var idInstrument: String = ""
)