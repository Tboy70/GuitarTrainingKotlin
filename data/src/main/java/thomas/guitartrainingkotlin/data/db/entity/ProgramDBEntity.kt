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
    var idProgram: String = "",
    var nameProgram: String = "",
    var descriptionProgram: String = "",
    var defaultProgram: Boolean = false,
    var userId: String? = "",
    var idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR,
    var exerciseList: List<ExerciseDBEntity>
)