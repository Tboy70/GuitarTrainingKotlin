package thomas.guitartrainingkotlin.data.entity.remote.program

import com.google.gson.annotations.SerializedName
import thomas.guitartrainingkotlin.data.entity.remote.exercise.ExerciseRemoteEntity
import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

data class ProgramRemoteEntity(
    @SerializedName("idProgram") var idProgram: String = "",
    @SerializedName("nameProgram") var nameProgram: String = "",
    @SerializedName("descriptionProgram") var descriptionProgram: String = "",
    @SerializedName("defaultProgram") var defaultProgram: Boolean = false,
    @SerializedName("userId") var userId: String? = "",
    @SerializedName("exercises") var exerciseRemoteEntityList: List<ExerciseRemoteEntity> = emptyList(),
    @SerializedName("idInstrument") var idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
)