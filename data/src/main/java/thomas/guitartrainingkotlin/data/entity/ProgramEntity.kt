package thomas.guitartrainingkotlin.data.entity

import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

data class ProgramEntity(
    var idProgram: String = "",
    val nameProgram: String = "",
    val descriptionProgram: String = "",
    val defaultProgram: Boolean = false,
    var userId: String? = "",   // userId can be null if it's default program
    var exerciseEntityList: List<ExerciseEntity> = emptyList(),
    val idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
)