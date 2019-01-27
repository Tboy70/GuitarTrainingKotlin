package thomas.example.com.data.entity.remote.program

import com.google.gson.annotations.SerializedName
import thomas.example.com.data.entity.remote.exercise.ExerciseRemoteEntity

data class ProgramRemoteEntity(
    @SerializedName("idProgram") var idProgram: String = "",
    @SerializedName("nameProgram") var nameProgram: String = "",
    @SerializedName("descriptionProgram") var descriptionProgram: String = "",
    @SerializedName("defaultProgram") var defaultProgram: Boolean = false,
    @SerializedName("userId") var userId: String? = "",
    @SerializedName("exercises") var exerciseRemoteEntityList: List<ExerciseRemoteEntity> = emptyList(),
    @SerializedName("idInstrument") var idInstrument: String = ""
)