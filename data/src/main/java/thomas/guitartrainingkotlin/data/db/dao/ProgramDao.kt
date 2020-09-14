package thomas.guitartrainingkotlin.data.db.dao

import androidx.room.*
import thomas.guitartrainingkotlin.data.db.entity.ProgramDBEntity

@Dao
interface ProgramDao {

    @Query("SELECT * FROM ProgramDBEntity")
    suspend fun retrieveProgramList(): List<ProgramDBEntity>

    @Query("SELECT * FROM ProgramDBEntity WHERE idProgram = :idProgram")
    suspend fun retrieveProgramById(idProgram: String): ProgramDBEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgramList(programDBEntityList: List<ProgramDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgram(programDBEntity: ProgramDBEntity)

    @Update
    suspend fun updateProgram(programDBEntity: ProgramDBEntity)

    @Query("DELETE FROM ProgramDBEntity WHERE idProgram = :idProgram")
    suspend fun deleteProgramById(idProgram: String)

    @Query("DELETE FROM ProgramDBEntity")
    suspend fun clearProgram()
}