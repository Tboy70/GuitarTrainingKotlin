package thomas.guitartrainingkotlin.data.entity.remote.program

import com.google.gson.annotations.SerializedName
import thomas.guitartrainingkotlin.data.entity.remote.exercise.ExerciseRemoteEntity
import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

data class ProgramRemoteEntity(
    @SerializedName("idProgram") val idProgram: String = "",
    @SerializedName("nameProgram") val nameProgram: String = "",
    @SerializedName("descriptionProgram") val descriptionProgram: String = "",
    @SerializedName("defaultProgram") val defaultProgram: Boolean = false,
    @SerializedName("userId") val userId: String? = "",
    @SerializedName("exercises") val exerciseRemoteEntityList: List<ExerciseRemoteEntity> = emptyList(),
    @SerializedName("idInstrument") val idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
)