package thomas.guitartrainingkotlin.data.manager.db

import thomas.guitartrainingkotlin.data.db.dao.*
import thomas.guitartrainingkotlin.data.db.entity.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DBManagerImpl @Inject constructor(
    private val userDao: UserDao,
    private val programDao: ProgramDao,
    private val exerciseDao: ExerciseDao,
    private val songDao: SongDao,
    private val scoreDao: ScoreDao
) : DBManager {

    override suspend fun insertUser(userDBEntity: UserDBEntity) {
        userDao.insertUser(userDBEntity)
    }

    override suspend fun clearUser() {
        userDao.clearUser()
    }

    override suspend fun retrieveUser(): UserDBEntity? {
        return userDao.retrieveUser()
    }

    override suspend fun retrieveProgramList(): List<ProgramDBEntity> {
        return programDao.retrieveProgramList()
    }

    override suspend fun retrieveProgramById(idProgram: String): ProgramDBEntity? {
        return programDao.retrieveProgramById(idProgram)
    }

    override suspend fun insertProgramList(programDBEntityList: List<ProgramDBEntity>) {
        programDao.insertProgramList(programDBEntityList)
    }

    override suspend fun insertProgram(programDBEntity: ProgramDBEntity) {
        programDao.insertProgram(programDBEntity)
    }

    override suspend fun updateProgram(programDBEntity: ProgramDBEntity) {
        programDao.updateProgram(programDBEntity)
    }

    override suspend fun deleteProgramById(idProgram: String) {
        programDao.deleteProgramById(idProgram)
    }

    override suspend fun deleteProgram() {
        programDao.clearProgram()
    }

    override suspend fun insertExerciseList(exerciseDBEntityList: List<ExerciseDBEntity>) {
        exerciseDao.insertExerciseList(exerciseDBEntityList)
    }

    override suspend fun deleteExercise() {
        exerciseDao.clearExercise()
    }

    override suspend fun retrieveSongList(): List<SongDBEntity> {
        return songDao.retrieveSongList()
    }

    override suspend fun retrieveSongById(idSong: String): SongDBEntity? {
        return songDao.retrieveSongById(idSong)
    }

    override suspend fun retrieveSongScore(idSong: String): List<ScoreDBEntity> {
        return scoreDao.retrieveSongScore(idSong)
    }

    override suspend fun updateSongScore(idSong: String, scoreDBEntityList: List<ScoreDBEntity>) {
        scoreDao.insertScoreDBEntityList(scoreDBEntityList)
    }

    override suspend fun insertSongList(songDBEntityList: List<SongDBEntity>) {
        songDao.insertSongList(songDBEntityList)
    }

    override suspend fun insertSong(songDBEntity: SongDBEntity) {
        songDao.insertSong(songDBEntity)
    }

    override suspend fun updateSong(songDBEntity: SongDBEntity) {
        songDao.updateSong(songDBEntity)
    }

    override suspend fun deleteSongById(idSong: String) {
        songDao.deleteSongById(idSong)
    }

    override suspend fun deleteSong() {
        songDao.clearSong()
    }

    override suspend fun clearDatabase() {
        userDao.clearUser()
        programDao.clearProgram()
        exerciseDao.clearExercise()
        songDao.clearSong()
        scoreDao.clearScore()
    }
}