package thomas.example.com.model

data class Program(
    var idProgram: String = "",
    var nameProgram: String = "",
    var descriptionProgram: String = "",
    var defaultProgram: Boolean = true,
    var idUser: String? = "",
    var exercises: MutableList<Exercise> = mutableListOf(),
    var idInstrument: String = ""
)