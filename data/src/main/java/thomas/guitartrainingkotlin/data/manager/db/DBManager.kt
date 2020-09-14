package thomas.guitartrainingkotlin.data.manager.db

import thomas.guitartrainingkotlin.data.db.entity.*


interface DBManager {

    suspend fun insertUser(userDBEntity: UserDBEntity)
    suspend fun retrieveUser() : UserDBEntity?
    suspend fun clearUser()

    suspend fun retrieveProgramList(): List<ProgramDBEntity>
    suspend fun retrieveProgramById(idProgram: String): ProgramDBEntity?
    suspend fun insertProgramList(programDBEntityList: List<ProgramDBEntity>)
    suspend fun insertProgram(programDBEntity: ProgramDBEntity)
    suspend fun updateProgram(programDBEntity: ProgramDBEntity)
    suspend fun deleteProgramById(idProgram: String)
    suspend fun deleteProgram()

    suspend fun insertExerciseList(exerciseDBEntityList: List<ExerciseDBEntity>)
    suspend fun deleteExercise()

    suspend fun retrieveSongList(): List<SongDBEntity>
    suspend fun retrieveSongById(idSong: String): SongDBEntity?
    suspend fun insertSongList(songDBEntityList: List<SongDBEntity>)
    suspend fun insertSong(songDBEntity: SongDBEntity)
    suspend fun updateSong(songDBEntity: SongDBEntity)
    suspend fun deleteSongById(idSong: String)
    suspend fun deleteSong()

    suspend fun retrieveSongScore(idSong: String): List<ScoreDBEntity>
    suspend fun updateSongScore(idSong: String, scoreDBEntityList: List<ScoreDBEntity>)

    suspend fun clearDatabase()
}