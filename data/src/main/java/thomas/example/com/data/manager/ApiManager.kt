package thomas.example.com.data.manager

import io.reactivex.Completable
import io.reactivex.Single
import thomas.example.com.data.entity.remote.exercise.ExerciseRemoteEntity
import thomas.example.com.data.entity.remote.program.ProgramRemoteEntity
import thomas.example.com.data.entity.remote.score.ScoreRemoteEntity
import thomas.example.com.data.entity.remote.song.ScoreFeedbackRemoteEntity
import thomas.example.com.data.entity.remote.song.SongRemoteEntity
import thomas.example.com.data.entity.remote.user.UserRemoteEntity

interface ApiManager {

    fun connectUser(userRemoteEntity: UserRemoteEntity): Single<UserRemoteEntity>

    fun retrieveUserById(idUser: String): Single<UserRemoteEntity>

    fun createNewUser(userRemoteEntity: UserRemoteEntity): Completable

    fun retrieveProgramsListByUserId(idUser: String, instrumentModeValue: Int): Single<List<ProgramRemoteEntity>>

    fun retrieveProgramFromId(idProgram: String): Single<ProgramRemoteEntity>

    fun createProgram(programRemoteEntity: ProgramRemoteEntity): Single<String>

    fun createExercise(listRemoteEntities: List<ExerciseRemoteEntity>): Completable

    fun updateProgram(programRemoteEntity: ProgramRemoteEntity): Completable

    fun updateExercise(exerciseRemoteEntities: List<ExerciseRemoteEntity>): Completable

    fun removeProgram(idProgram: String): Completable

    fun removeExercises(exercisesRemoteEntitiesToBeRemoved: List<ExerciseRemoteEntity>): Completable

    fun retrieveSongsListByUserId(idUser: String, instrumentModeValue: Int): Single<List<SongRemoteEntity>>

    fun retrieveSongFromId(idSong: String): Single<SongRemoteEntity>

    fun createSong(songRemoteEntity: SongRemoteEntity): Completable

    fun updateSong(songRemoteEntity: SongRemoteEntity): Completable

    fun removeSong(idSong: String): Completable

    fun sendScoreFeedback(scoreFeedbackRemoteEntity: ScoreFeedbackRemoteEntity, idSong: String): Completable

    fun retrieveSongScoreHistoric(idSong: String): Single<List<ScoreRemoteEntity>>

    fun suppressAccount(idUser: String): Completable
}