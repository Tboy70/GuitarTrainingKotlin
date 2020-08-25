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

    override fun insertUser(userDBEntity: UserDBEntity) {
        userDao.insertUser(userDBEntity)
    }

    override fun clearUser() {
        userDao.clearUser()
    }

    override fun retrieveUser(): UserDBEntity? {
        return userDao.retrieveUser()
    }

    override fun retrieveProgramList(): List<ProgramDBEntity> {
        return programDao.retrieveProgramList()
    }

    override fun retrieveProgramById(idProgram: String): ProgramDBEntity? {
        return programDao.retrieveProgramById(idProgram)
    }

    override fun insertProgramList(programDBEntityList: List<ProgramDBEntity>) {
        programDao.insertProgramList(programDBEntityList)
    }

    override fun insertProgram(programDBEntity: ProgramDBEntity) {
        programDao.insertProgram(programDBEntity)
    }

    override fun updateProgram(programDBEntity: ProgramDBEntity) {
        programDao.updateProgram(programDBEntity)
    }

    override fun deleteProgramById(idProgram: String) {
        programDao.deleteProgramById(idProgram)
    }

    override fun deleteProgram() {
        programDao.clearProgram()
    }

    override fun insertExerciseList(exerciseDBEntityList: List<ExerciseDBEntity>) {
        exerciseDao.insertExerciseList(exerciseDBEntityList)
    }

    override fun deleteExercise() {
        exerciseDao.clearExercise()
    }

    override fun retrieveSongList(): List<SongDBEntity> {
        return songDao.retrieveSongList()
    }

    override fun retrieveSongById(idSong: String): SongDBEntity? {
        return songDao.retrieveSongById(idSong)
    }

    override fun retrieveSongScore(idSong: String): List<ScoreDBEntity> {
        return scoreDao.retrieveSongScore(idSong)
    }

    override fun updateSongScore(idSong: String, scoreDBEntityList: List<ScoreDBEntity>) {
        scoreDao.insertScoreDBEntityList(scoreDBEntityList)
    }

    override fun insertSongList(songDBEntityList: List<SongDBEntity>) {
        songDao.insertSongList(songDBEntityList)
    }

    override fun insertSong(songDBEntity: SongDBEntity) {
        songDao.insertSong(songDBEntity)
    }

    override fun updateSong(songDBEntity: SongDBEntity) {
        songDao.updateSong(songDBEntity)
    }

    override fun deleteSongById(idSong: String) {
        songDao.deleteSongById(idSong)
    }

    override fun deleteSong() {
        songDao.clearSong()
    }

    override fun clearDatabase() {
        userDao.clearUser()
        programDao.clearProgram()
        exerciseDao.clearExercise()
        songDao.clearSong()
        scoreDao.clearScore()
    }
}