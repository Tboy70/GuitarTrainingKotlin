package thomas.guitartrainingkotlin.domain.model

import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

data class Program(
    val idProgram: String = "",
    val nameProgram: String = "",
    val descriptionProgram: String = "",
    val defaultProgram: Boolean = true,
    var userId: String? = "",
    val exercises: MutableList<Exercise> = mutableListOf(),
    val idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
)