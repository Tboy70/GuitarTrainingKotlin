package thomas.guitartrainingkotlin.data.manager.db

import thomas.guitartrainingkotlin.data.entity.db.ExerciseDBEntity
import thomas.guitartrainingkotlin.data.entity.db.ProgramDBEntity
import thomas.guitartrainingkotlin.data.entity.db.ScoreDBEntity
import thomas.guitartrainingkotlin.data.entity.db.SongDBEntity

interface DBManager {

    fun retrieveProgramList(): List<ProgramDBEntity>
    fun retrieveProgramById(idProgram: String): ProgramDBEntity?
    fun insertProgramList(programDBEntityList: List<ProgramDBEntity>)
    fun insertProgram(programDBEntity: ProgramDBEntity)
    fun updateProgram(programDBEntity: ProgramDBEntity)
    fun deleteProgramById(idProgram: String)
    fun deleteProgram()

    fun insertExerciseList(exerciseDBEntityList: List<ExerciseDBEntity>)
    fun deleteExercise()

    fun retrieveSongList(): List<SongDBEntity>
    fun retrieveSongById(idSong: String): SongDBEntity?
    fun insertSongList(songDBEntityList: List<SongDBEntity>)
    fun insertSong(songDBEntity: SongDBEntity)
    fun updateSong(songDBEntity: SongDBEntity)
    fun deleteSongById(idSong: String)
    fun deleteSong()

    fun retrieveSongScore(idSong: String): List<ScoreDBEntity>
    fun updateSongScore(idSong: String, scoreDBEntityList: List<ScoreDBEntity>)
}