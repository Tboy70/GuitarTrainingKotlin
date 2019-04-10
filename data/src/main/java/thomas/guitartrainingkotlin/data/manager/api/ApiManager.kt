package thomas.guitartrainingkotlin.data.manager.api

import io.reactivex.Completable
import io.reactivex.Single
import thomas.guitartrainingkotlin.data.entity.remote.exercise.ExerciseRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.program.ProgramRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.score.ScoreRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.song.ScoreFeedbackRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.song.SongRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.user.UserRemoteEntity

interface ApiManager {

    // User
    fun connectUser(userRemoteEntity: UserRemoteEntity): Single<UserRemoteEntity>

    fun createNewUser(userRemoteEntity: UserRemoteEntity): Completable

    fun retrieveUserById(userId: String): Single<UserRemoteEntity>

    fun retrievePassword(emailAddress: String): Completable


    // Program
    fun retrieveProgramsListByUserId(userId: String, instrumentModeValue: Int): Single<List<ProgramRemoteEntity>>

    fun retrieveProgramFromId(idProgram: String): Single<ProgramRemoteEntity>

    fun createProgram(programRemoteEntity: ProgramRemoteEntity): Single<String>

    fun createExercise(exerciseRemoteEntityList: List<ExerciseRemoteEntity>): Completable

    fun updateProgram(programRemoteEntity: ProgramRemoteEntity): Completable

    fun updateExercise(exerciseRemoteEntityList: List<ExerciseRemoteEntity>): Completable

    fun removeProgram(idProgram: String): Completable

    fun removeExercises(exerciseRemoteEntityListToBeRemoved: List<ExerciseRemoteEntity>): Completable


    // Song
    fun retrieveSongListByUserId(userId: String, instrumentModeValue: Int): Single<List<SongRemoteEntity>>

    fun retrieveSongFromId(idSong: String): Single<SongRemoteEntity>

    fun createSong(songRemoteEntity: SongRemoteEntity): Completable

    fun updateSong(songRemoteEntity: SongRemoteEntity): Completable

    fun removeSong(idSong: String): Completable

    fun sendScoreFeedback(scoreFeedbackRemoteEntity: ScoreFeedbackRemoteEntity, idSong: String): Completable

    fun retrieveSongScoreHistory(idSong: String): Single<List<ScoreRemoteEntity>>

    fun suppressAccount(userId: String): Completable
}