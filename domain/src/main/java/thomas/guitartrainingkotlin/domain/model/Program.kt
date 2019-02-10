package thomas.guitartrainingkotlin.domain.model

import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

data class Program(
    var idProgram: String = "",
    var nameProgram: String = "",
    var descriptionProgram: String = "",
    var defaultProgram: Boolean = true,
    var userId: String? = "",
    var exercises: MutableList<Exercise> = mutableListOf(),
    var idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
)