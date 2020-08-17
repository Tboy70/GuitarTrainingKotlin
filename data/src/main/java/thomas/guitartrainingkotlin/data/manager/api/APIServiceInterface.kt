package thomas.guitartrainingkotlin.data.manager.api

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
    suspend fun connectUser(@Body userRemoteEntity: UserRemoteEntity): UserRemoteEntity

    @POST("user")
    suspend fun createNewUser(@Body userRemoteEntity: UserRemoteEntity)

    @GET("user/{userId}")
    suspend fun retrieveUserById(@Path("userId") userId: String): UserRemoteEntity

    @DELETE("user/{userId}")
    suspend fun suppressAccount(@Path("userId") userId: String)

    @GET("programs/{userId}/{instrumentMode}")
    suspend fun retrieveProgramsListByUserId(
        @Path("userId") userId: String,
        @Path("instrumentMode") instrumentModeValue: Int
    ): List<ProgramRemoteEntity>

    @GET("program/{idProgram}")
    suspend fun retrieveProgramFromId(@Path("idProgram") idProgram: String): ProgramRemoteEntity

    @POST("program")
    suspend fun createProgram(@Body programRemoteEntity: ProgramRemoteEntity) : ProgramResponseRemoteEntity

    @POST("exercise")
    suspend fun createExercise(@Body exerciseRemoteEntity: List<ExerciseRemoteEntity>)

    @PATCH("program/{idProgram}")
    suspend fun updateProgram(
        @Path("idProgram") idProgram: String,
        @Body programRemoteEntity: ProgramRemoteEntity
    )

    @PATCH("exercise")
    suspend fun updateExercise(@Body exerciseRemoteEntityList: List<ExerciseRemoteEntity>)

    @DELETE("program/{idProgram}")
    suspend fun removeProgram(@Path("idProgram") idProgram: String)

    @HTTP(method = "DELETE", path = "exercise", hasBody = true)
    suspend fun removeExercises(@Body exerciseRemoteEntityListToBeRemoved: List<ExerciseRemoteEntity>)

    @GET("songs/{userId}/{instrumentMode}")
    suspend fun retrieveSongListByUserId(
        @Path("userId") userId: String,
        @Path("instrumentMode") instrumentModeValue: Int
    ): List<SongRemoteEntity>

    @GET("song/{idSong}")
    suspend fun retrieveSongFromId(@Path("idSong") idSong: String): SongRemoteEntity

    @POST("song")
    suspend fun createSong(@Body songRemoteEntity: SongRemoteEntity)

    @PATCH("song/{idSong}")
    suspend fun updateSong(
        @Path("idSong") idSong: String,
        @Body songRemoteEntity: SongRemoteEntity
    )

    @DELETE("song/{idSong}")
    suspend fun removeSong(@Path("idSong") idProgram: String)

    @POST("song/{idSong}")
    suspend fun sendScoreFeedback(
        @Body scoreFeedback: ScoreFeedbackRemoteEntity,
        @Path("idSong") idSong: String
    )

    @GET("score/{idSong}")
    suspend fun retrieveSongScoreHistory(@Path("idSong") idSong: String): List<ScoreRemoteEntity>

    @POST("user/{emailAddress}")
    suspend fun retrievePassword(@Path("emailAddress") emailAddress: String)
}