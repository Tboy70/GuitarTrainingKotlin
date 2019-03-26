package thomas.guitartrainingkotlin.data.entity.db

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.OneToMany
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.kotlinextensions.*
import thomas.guitartrainingkotlin.data.manager.db.DBManagerImpl
import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

@Table(database = DBManagerImpl::class)
data class ProgramDBEntity(

    @PrimaryKey
    var idProgram: String = "",

    @Column
    var nameProgram: String = "",

    @Column
    var descriptionProgram: String = "",

    @Column(getterName = "getDefaultProgram")
    var defaultProgram: Boolean = false,

    @Column
    var userId: String? = "",

    @Column
    var idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR

) {

    @get:OneToMany(methods = [OneToMany.Method.ALL], efficientMethods = true)
    var exerciseList by oneToMany { select from ExerciseDBEntity::class where (ExerciseDBEntity_Table.idProgram eq idProgram) }

    override fun toString(): String {
        return "ProgramDBEntity(idProgram='$idProgram', nameProgram='$nameProgram', descriptionProgram='$descriptionProgram', defaultProgram=$defaultProgram, userId=$userId, idInstrument=$idInstrument)"
    }

}