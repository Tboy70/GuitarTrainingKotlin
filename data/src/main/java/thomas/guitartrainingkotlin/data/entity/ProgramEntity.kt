package thomas.guitartrainingkotlin.data.entity

import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

data class ProgramEntity(
    var idProgram: String = "",
    var nameProgram: String = "",
    var descriptionProgram: String = "",
    var defaultProgram: Boolean = false,
    var userId: String? = "",
    var exerciseEntityList: List<ExerciseEntity> = emptyList(),
    var idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
)