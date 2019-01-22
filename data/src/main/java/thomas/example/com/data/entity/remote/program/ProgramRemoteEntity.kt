package thomas.example.com.data.entity.remote.program

import com.google.gson.annotations.SerializedName
import thomas.example.com.data.entity.remote.exercise.ExerciseRemoteEntity

data class ProgramRemoteEntity(
    @SerializedName("idProgram") var idProgram: String = "",
    @SerializedName("nameProgram") var nameProgram: String = "",
    @SerializedName("descriptionProgram") var descriptionProgram: String = "",
    @SerializedName("defaultProgram") var defaultProgram: Boolean = false,
    @SerializedName("userId") var idUser: String? = "",
    @SerializedName("exercises") var exerciseRemoteEntities: List<ExerciseRemoteEntity> = mutableListOf(),
    @SerializedName("idInstrument") var idInstrument: String = ""
)