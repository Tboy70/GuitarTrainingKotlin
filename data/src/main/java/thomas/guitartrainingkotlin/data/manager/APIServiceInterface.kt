package thomas.guitartrainingkotlin.data.manager

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*
import thomas.guitartrainingkotlin.data.entity.remote.exercise.ExerciseRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.program.ProgramRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.program.ProgramResponseRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.score.ScoreRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.song.ScoreFeedbackRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.song.SongRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.user.UserRemoteEntity

interface APIServiceInterface {

    @POST("connect")
    fun connectUser(@Body userRemoteEntity: UserRemoteEntity): Single<UserRemoteEntity>

    @POST("user")
    fun createNewUser(@Body userRemoteEntity: UserRemoteEntity): Completable

    @GET("user/{userId}")
    fun retrieveUserById(@Path("userId") userId: String): Single<UserRemoteEntity>

    @DELETE("user/{userId}")
    fun suppressAccount(@Path("userId") userId: String): Completable

    @GET("programs/{userId}/{instrumentMode}")
    fun retrieveProgramsListByUserId(@Path("userId") userId: String, @Path("instrumentMode") instrumentModeValue: Int): Single<List<ProgramRemoteEntity>>

    @GET("program/{idProgram}")
    fun retrieveProgramFromId(@Path("idProgram") idProgram: String): Single<Response<ProgramRemoteEntity>>

    @POST("program")
    fun createProgram(@Body programRemoteEntity: ProgramRemoteEntity): Single<Response<ProgramResponseRemoteEntity>>

    @POST("exercise")
    fun createExercise(@Body exerciseRemoteEntity: List<ExerciseRemoteEntity>): Completable

    @PATCH("program/{idProgram}")
    fun updateProgram(@Path("idProgram") idProgram: String, @Body programRemoteEntity: ProgramRemoteEntity): Completable

    @PATCH("exercise")
    fun updateExercise(@Body exerciseRemoteEntityList: List<ExerciseRemoteEntity>): Completable

    @DELETE("program/{idProgram}")
    fun removeProgram(@Path("idProgram") idProgram: String): Completable

    @HTTP(method = "DELETE", path = "exercise", hasBody = true)
    fun removeExercises(@Body exerciseRemoteEntityListToBeRemoved: List<ExerciseRemoteEntity>): Completable

    @GET("songs/{userId}/{instrumentMode}")
    fun retrieveSongListByUserId(@Path("userId") userId: String, @Path("instrumentMode") instrumentModeValue: Int): Single<List<SongRemoteEntity>>

    @GET("song/{idSong}")
    fun retrieveSongFromId(@Path("idSong") idSong: String): Single<SongRemoteEntity>

    @POST("song")
    fun createSong(@Body songRemoteEntity: SongRemoteEntity): Completable

    @PATCH("song/{idSong}")
    fun updateSong(@Path("idSong") idSong: String, @Body songRemoteEntity: SongRemoteEntity): Completable

    @DELETE("song/{idSong}")
    fun removeSong(@Path("idSong") idProgram: String): Completable

    @POST("song/{idSong}")
    fun sendScoreFeedback(@Body scoreFeedback: ScoreFeedbackRemoteEntity, @Path("idSong") idSong: String): Completable

    @GET("score/{idSong}")
    fun retrieveSongScoreHistory(@Path("idSong") idSong: String): Single<List<ScoreRemoteEntity>>
}