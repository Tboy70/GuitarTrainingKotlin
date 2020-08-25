package thomas.guitartrainingkotlin.data.db.dao

import androidx.room.*
import thomas.guitartrainingkotlin.data.db.entity.ProgramDBEntity

@Dao
interface ProgramDao {

    @Query("SELECT * FROM ProgramDBEntity")
    fun retrieveProgramList(): List<ProgramDBEntity>

    @Query("SELECT * FROM ProgramDBEntity WHERE idProgram = :idProgram")
    fun retrieveProgramById(idProgram: String): ProgramDBEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProgramList(programDBEntityList: List<ProgramDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProgram(programDBEntity: ProgramDBEntity)

    @Update
    fun updateProgram(programDBEntity: ProgramDBEntity)

    @Query("DELETE FROM ProgramDBEntity WHERE idProgram = :idProgram")
    fun deleteProgramById(idProgram: String)

    @Query("DELETE FROM ProgramDBEntity")
    fun clearProgram()
}