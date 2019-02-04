package thomas.example.com.data.manager

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.scottyab.aescrypt.AESCrypt
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import thomas.example.com.data.entity.remote.exercise.ExerciseRemoteEntity
import thomas.example.com.data.entity.remote.program.ProgramRemoteEntity
import thomas.example.com.data.entity.remote.program.ProgramResponseRemoteEntity
import thomas.example.com.data.entity.remote.score.ScoreRemoteEntity
import thomas.example.com.data.entity.remote.song.ScoreFeedbackRemoteEntity
import thomas.example.com.data.entity.remote.song.SongRemoteEntity
import thomas.example.com.data.entity.remote.user.UserRemoteEntity
import thomas.example.com.utils.ConstantErrors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiManagerImpl @Inject constructor() : ApiManager {
    private var apiService: APIServiceInterface

    companion object {
        const val BASE_URL = "http://thomasboy.fr/guitar_api/public/"
    }

    init {
        val gson: Gson = GsonBuilder().setLenient().create()
        val rxAdapter: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxAdapter)
            .client(client)
            .build()
        apiService = retrofit.create(APIServiceInterface::class.java)
    }

    override fun connectUser(userRemoteEntity: UserRemoteEntity): Single<UserRemoteEntity> {
        userRemoteEntity.userPassword = AESCrypt.encrypt(userRemoteEntity.userPseudo, userRemoteEntity.userPassword)
        return apiService.connectUser(userRemoteEntity)
    }

    override fun createNewUser(userRemoteEntity: UserRemoteEntity): Completable {
        userRemoteEntity.userPassword = AESCrypt.encrypt(userRemoteEntity.userPseudo, userRemoteEntity.userPassword)
        return apiService.createNewUser(userRemoteEntity)
    }

    override fun retrieveUserById(userId: String): Single<UserRemoteEntity> {
        return apiService.retrieveUserById(userId)
    }

    override fun suppressAccount(userId: String): Completable {
        return apiService.suppressAccount(userId)
    }

    override fun retrieveProgramsListByUserId(userId: String, instrumentModeValue: Int)
            : Single<List<ProgramRemoteEntity>> {
        return apiService.retrieveProgramsListByUserId(userId, instrumentModeValue)
    }

    override fun retrieveProgramFromId(idProgram: String): Single<ProgramRemoteEntity> {
        return apiService.retrieveProgramFromId(idProgram).map {
            if (it.body() != null) {
                it.body()
            } else {
                throw Exception(ConstantErrors.ERROR_RETRIEVE_PROGRAM)
            }
        }
    }

    override fun createProgram(programRemoteEntity: ProgramRemoteEntity): Single<String> {
        return apiService.createProgram(programRemoteEntity).map {
            if (it.isSuccessful && it.body() != null) { // TODO : Check this if / else
                (it.body() as ProgramResponseRemoteEntity).getCreatedId()
            } else {
                throw Exception(ConstantErrors.ERROR_CREATION_PROGRAM)
            }
        }
    }

    override fun createExercise(exerciseRemoteEntityList: List<ExerciseRemoteEntity>): Completable {
        return apiService.createExercise(exerciseRemoteEntityList)
    }

    override fun updateProgram(programRemoteEntity: ProgramRemoteEntity): Completable {
        return apiService.updateProgram(programRemoteEntity.idProgram, programRemoteEntity)
    }

    override fun updateExercise(exerciseRemoteEntityList: List<ExerciseRemoteEntity>): Completable {
        return apiService.updateExercise(exerciseRemoteEntityList)
    }

    override fun removeProgram(idProgram: String): Completable {
        return apiService.removeProgram(idProgram)
    }

    override fun removeExercises(exerciseRemoteEntityListToBeRemoved: List<ExerciseRemoteEntity>): Completable {
        return apiService.removeExercises(exerciseRemoteEntityListToBeRemoved)
    }

    override fun retrieveSongListByUserId(
        userId: String,
        instrumentModeValue: Int
    ): Single<List<SongRemoteEntity>> {
        return apiService.retrieveSongListByUserId(userId, instrumentModeValue)
    }

    override fun retrieveSongFromId(idSong: String): Single<SongRemoteEntity> {
        return apiService.retrieveSongFromId(idSong)
    }

    override fun createSong(songRemoteEntity: SongRemoteEntity): Completable {
        return apiService.createSong(songRemoteEntity)
    }

    override fun updateSong(songRemoteEntity: SongRemoteEntity): Completable {
        return apiService.updateSong(songRemoteEntity.idSong, songRemoteEntity)
    }

    override fun removeSong(idSong: String): Completable {
        return apiService.removeSong(idSong)
    }

    override fun sendScoreFeedback(
        scoreFeedbackRemoteEntity: ScoreFeedbackRemoteEntity,
        idSong: String
    ): Completable {
        return apiService.sendScoreFeedback(scoreFeedbackRemoteEntity, idSong)
    }

    override fun retrieveSongScoreHistory(idSong: String): Single<List<ScoreRemoteEntity>> {
        return apiService.retrieveSongScoreHistory(idSong)
    }
}