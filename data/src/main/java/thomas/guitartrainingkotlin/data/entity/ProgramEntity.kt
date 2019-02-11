package thomas.guitartrainingkotlin.data.entity

import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

data class ProgramEntity(
    val idProgram: String = "",
    val nameProgram: String = "",
    val descriptionProgram: String = "",
    val defaultProgram: Boolean = false,
    val userId: String? = "",
    val exerciseEntityList: List<ExerciseEntity> = emptyList(),
    val idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
)