package thomas.guitartrainingkotlin.data.manager.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.scottyab.aescrypt.AESCrypt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thomas.guitartrainingkotlin.data.entity.remote.exercise.ExerciseRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.program.ProgramRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.program.ProgramResponseRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.score.ScoreRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.song.ScoreFeedbackRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.song.SongRemoteEntity
import thomas.guitartrainingkotlin.data.entity.remote.user.UserRemoteEntity
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
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        apiService = retrofit.create(APIServiceInterface::class.java)
    }

    override fun connectUser(userRemoteEntity: UserRemoteEntity): Flow<UserRemoteEntity> {
        userRemoteEntity.userPassword =
            AESCrypt.encrypt(userRemoteEntity.userPseudo, userRemoteEntity.userPassword)
        return flow {
            emit(apiService.connectUser(userRemoteEntity))
        }
    }

    override fun createNewUser(userRemoteEntity: UserRemoteEntity): Flow<Unit> {
        userRemoteEntity.userPassword =
            AESCrypt.encrypt(userRemoteEntity.userPseudo, userRemoteEntity.userPassword)
        return flow {
            emit(apiService.createNewUser(userRemoteEntity))
        }
    }

    override fun retrieveUserById(userId: String): Flow<UserRemoteEntity> {
        return flow {
            emit(apiService.retrieveUserById(userId))
        }
    }

    override fun retrievePassword(emailAddress: String): Flow<Unit> {
        return flow {
            emit(apiService.retrievePassword(emailAddress))
        }
    }

    override fun suppressAccount(userId: String): Flow<Unit> {
        return flow {
            emit(apiService.suppressAccount(userId))
        }
    }

    override fun retrieveProgramsListByUserId(userId: String, instrumentModeValue: Int)
            : Flow<List<ProgramRemoteEntity>> {
        return flow {
            emit(apiService.retrieveProgramsListByUserId(userId, instrumentModeValue))
        }
    }

    override fun retrieveProgramFromId(idProgram: String): Flow<ProgramRemoteEntity> {
        return flow {
            emit(apiService.retrieveProgramFromId(idProgram))
        }
    }

    override fun createProgram(programRemoteEntity: ProgramRemoteEntity): Flow<String> {
        return flow {
            emit(apiService.createProgram(programRemoteEntity).getCreatedId())
        }
    }

    override fun createExercise(exerciseRemoteEntityList: List<ExerciseRemoteEntity>): Flow<Unit> {
        return flow {
            emit(apiService.createExercise(exerciseRemoteEntityList))
        }
    }

    override fun updateProgram(programRemoteEntity: ProgramRemoteEntity): Flow<Unit> {
        return flow {
            emit(apiService.updateProgram(programRemoteEntity.idProgram, programRemoteEntity))
        }
    }

    override fun updateExercise(exerciseRemoteEntityList: List<ExerciseRemoteEntity>): Flow<Unit> {
        return flow {
            apiService.updateExercise(exerciseRemoteEntityList)
        }
    }

    override fun removeProgram(idProgram: String): Flow<Unit> {
        return flow {
            emit(apiService.removeProgram(idProgram))
        }
    }

    override fun removeExercises(exerciseRemoteEntityListToBeRemoved: List<ExerciseRemoteEntity>): Flow<Unit> {
        return flow {
            emit(apiService.removeExercises(exerciseRemoteEntityListToBeRemoved))
        }
    }

    override fun retrieveSongListByUserId(
        userId: String,
        instrumentModeValue: Int
    ): Flow<List<SongRemoteEntity>> {
        return flow {
            emit(apiService.retrieveSongListByUserId(userId, instrumentModeValue))
        }
    }

    override fun retrieveSongFromId(idSong: String): Flow<SongRemoteEntity> {
        return flow {
            emit(apiService.retrieveSongFromId(idSong))
        }
    }

    override fun createSong(songRemoteEntity: SongRemoteEntity): Flow<Unit> {
        return flow {
            emit(apiService.createSong(songRemoteEntity))
        }
    }

    override fun updateSong(songRemoteEntity: SongRemoteEntity): Flow<Unit> {
        return flow {
            emit(apiService.updateSong(songRemoteEntity.idSong, songRemoteEntity))
        }
    }

    override fun removeSong(idSong: String): Flow<Unit> {
        return flow {
            emit(apiService.removeSong(idSong))
        }
    }

    override fun sendScoreFeedback(
        scoreFeedbackRemoteEntity: ScoreFeedbackRemoteEntity,
        idSong: String
    ): Flow<Unit> {
        return flow {
            emit(apiService.sendScoreFeedback(scoreFeedbackRemoteEntity, idSong))
        }
    }

    override fun retrieveSongScoreHistory(idSong: String): Flow<List<ScoreRemoteEntity>> {
        return flow {
            emit(apiService.retrieveSongScoreHistory(idSong))
        }
    }
}