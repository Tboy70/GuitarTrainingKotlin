package thomas.guitartrainingkotlin.data.entity.remote.exercise

import com.google.gson.annotations.SerializedName

data class ExerciseRemoteEntity(
    @SerializedName("idExercise") val idExercise: String = "",
    @SerializedName("durationExercise") val durationExercise: Int = 0,
    @SerializedName("idProgram") val idProgram: String = "",
    @SerializedName("typeExercise") val typeExercise: Int = 0
)