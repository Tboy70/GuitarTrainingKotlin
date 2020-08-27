package thomas.guitartrainingkotlin.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import thomas.guitartrainingkotlin.data.db.converter.ExerciseConverter
import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

@Entity
@TypeConverters(ExerciseConverter::class)
class ProgramDBEntity(

    @PrimaryKey
    val idProgram: String = "",
    val nameProgram: String = "",
    val descriptionProgram: String = "",
    val defaultProgram: Boolean = false,
    val userId: String? = "",   // userId can be null if it's default program
    val exerciseList: List<ExerciseDBEntity> = emptyList(),
    val idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
)