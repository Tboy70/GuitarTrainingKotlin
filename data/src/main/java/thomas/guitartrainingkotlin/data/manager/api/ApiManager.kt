package thomas.guitartrainingkotlin.data.manager.api

import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import thomas.guitartrainingkotlin.data.entity.remote.exercise.ExerciseRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.program.ProgramRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.score.ScoreRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.song.ScoreFeedbackRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.song.SongRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.user.UserRemoteEntity

interface ApiManager {

    // User
    fun connectUser(userRemoteEntity: UserRemoteEntity): Flow<UserRemoteEntity>

    fun createNewUser(userRemoteEntity: UserRemoteEntity) : Flow<Unit>

    fun retrieveUserById(userId: String): Flow<UserRemoteEntity>

    fun retrievePassword(emailAddress: String): Flow<Unit>

    fun suppressAccount(userId: String): Flow<Unit>


    // Program
    fun retrieveProgramsListByUserId(userId: String, instrumentModeValue: Int): Flow<List<ProgramRemoteEntity>>

    fun retrieveProgramFromId(idProgram: String): Flow<ProgramRemoteEntity>

    fun createProgram(programRemoteEntity: ProgramRemoteEntity): Flow<String?>

    fun createExercise(exerciseRemoteEntityList: List<ExerciseRemoteEntity>): Flow<Unit>

    fun updateProgram(programRemoteEntity: ProgramRemoteEntity): Flow<Unit>

    fun updateExercise(exerciseRemoteEntityList: List<ExerciseRemoteEntity>): Flow<Unit>

    fun removeProgram(idProgram: String): Flow<Unit>

    fun removeExercises(exerciseRemoteEntityListToBeRemoved: List<ExerciseRemoteEntity>): Flow<Unit>


    // Song
    fun retrieveSongListByUserId(userId: String, instrumentModeValue: Int): Flow<List<SongRemoteEntity>>

    fun retrieveSongFromId(idSong: String): Flow<SongRemoteEntity>

    fun createSong(songRemoteEntity: SongRemoteEntity): Flow<Unit>

    fun updateSong(songRemoteEntity: SongRemoteEntity): Flow<Unit>

    fun removeSong(idSong: String): Flow<Unit>

    fun sendScoreFeedback(scoreFeedbackRemoteEntity: ScoreFeedbackRemoteEntity, idSong: String): Flow<Unit>

    fun retrieveSongScoreHistory(idSong: String): Flow<List<ScoreRemoteEntity>>
}