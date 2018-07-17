package thomas.example.com.data.entity

data class ProgramEntity(var idProgram: String = "",
                         var nameProgram: String = "",
                         var descriptionProgram: String = "",
                         var defaultProgram: Boolean = false,
                         var idUser: String = "",
                         var exerciseEntities: List<ExerciseEntity> = mutableListOf())