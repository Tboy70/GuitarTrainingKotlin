package thomas.example.com.data.entity.remote.exercise

import com.google.gson.annotations.SerializedName

data class ExerciseRemoteEntity(
    @SerializedName("idExercise") var idExercise: String = "",
    @SerializedName("durationExercise") var durationExercise: Int = 0,
    @SerializedName("idProgram") var idProgram: String = "",
    @SerializedName("typeExercise") var typeExercise: Int = 0
)