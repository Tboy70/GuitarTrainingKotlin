package thomas.example.com.data.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.scottyab.aescrypt.AESCrypt
import io.reactivex.Observable
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
import thomas.example.com.data.entity.remote.song.SongResponseRemoteEntity
import thomas.example.com.data.entity.remote.user.UserRemoteEntity
import thomas.example.com.data.entity.remote.user.UserResponseRemoteEntity
import thomas.example.com.utils.ConstantErrors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiModuleImpl @Inject constructor() : ApiModule {
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

    override fun connectUser(userRemoteEntity: UserRemoteEntity): Observable<UserRemoteEntity> {
        userRemoteEntity.passwordUser = AESCrypt.encrypt(userRemoteEntity.pseudoUser, userRemoteEntity.passwordUser)
        return apiService.connectUser(userRemoteEntity).map {
            if (it.body() != null) {
                it.body()
            } else {
                throw Exception(ConstantErrors.ERROR_CONNECT_USER)
            }
        }
    }

    override fun retrieveUserById(idUser: String): Observable<UserRemoteEntity> {
        return apiService.retrieveUserById(idUser).map {
            if (it.isSuccessful && it.body() != null) {
                it.body()
            } else {
                throw Exception(ConstantErrors.ERROR_RETRIEVE_USER)
            }
        }
    }

    override fun createNewUser(userRemoteEntity: UserRemoteEntity): Observable<String> {
        userRemoteEntity.passwordUser = AESCrypt.encrypt(userRemoteEntity.pseudoUser, userRemoteEntity.passwordUser)
        return apiService.createNewUser(userRemoteEntity).map {
            if (it.isSuccessful && it.body() != null) {
                (it.body() as UserResponseRemoteEntity).getCreatedId()
            } else {
                throw Exception(ConstantErrors.ERROR_CREATION_USER)
            }
        }
    }

    override fun suppressAccount(idUser: String): Observable<Boolean> {
        return apiService.suppressAccount(idUser).map {
            if (it.isSuccessful) {
                it.isSuccessful
            } else {
                throw Exception(ConstantErrors.ERROR_REMOVE_USER)
            }
        }
    }

    override fun retrieveProgramsListByUserId(
        idUser: String,
        instrumentModeValue: Int
    ): Observable<List<ProgramRemoteEntity>> {
        return apiService.retrieveProgramsListByUserId(idUser, instrumentModeValue).map {
            if (it.body() != null) {
                it.body()
            } else {
                throw Exception(ConstantErrors.ERROR_RETRIEVE_PROGRAMS)
            }
        }
    }

    override fun retrieveProgramFromId(idProgram: String): Observable<ProgramRemoteEntity> {
        return apiService.retrieveProgramFromId(idProgram).map {
            if (it.body() != null) {
                it.body()
            } else {
                throw Exception(ConstantErrors.ERROR_RETRIEVE_PROGRAM)
            }
        }
    }

    override fun createProgram(programRemoteEntity: ProgramRemoteEntity): Observable<String> {
        return apiService.createProgram(programRemoteEntity).map {
            if (it.isSuccessful && it.body() != null) {
                (it.body() as ProgramResponseRemoteEntity).getCreatedId()
            } else {
                throw Exception(ConstantErrors.ERROR_CREATION_PROGRAM)
            }
        }
    }

    override fun createExercise(listRemoteEntities: List<ExerciseRemoteEntity>): Observable<Boolean> {
        return apiService.createExercise(listRemoteEntities).map {
            if (it.isSuccessful) {
                it.isSuccessful
            } else {
                throw Exception(ConstantErrors.ERROR_CREATION_EXERCISE)
            }
        }
    }

    override fun updateProgram(programRemoteEntity: ProgramRemoteEntity): Observable<Boolean> {
        return apiService.updateProgram(programRemoteEntity.idProgram, programRemoteEntity).map {
            if (it.isSuccessful) {
                it.isSuccessful
            } else {
                throw Exception(ConstantErrors.ERROR_UPDATE_PROGRAM)
            }
        }
    }

    override fun updateExercise(exerciseRemoteEntities: List<ExerciseRemoteEntity>): Observable<Boolean> {
        return apiService.updateExercise(exerciseRemoteEntities).map {
            if (it.isSuccessful) {
                it.isSuccessful
            } else {
                throw Exception(ConstantErrors.ERROR_UPDATE_EXERCISE)
            }
        }
    }

    override fun removeProgram(idProgram: String): Observable<Boolean> {
        return apiService.removeProgram(idProgram).map {
            if (it.isSuccessful) {
                it.isSuccessful
            } else {
                throw Exception(ConstantErrors.ERROR_REMOVE_PROGRAM)
            }
        }
    }

    override fun removeExercises(exercisesRemoteEntitiesToBeRemoved: List<ExerciseRemoteEntity>): Observable<Boolean> {
        return apiService.removeExercises(exercisesRemoteEntitiesToBeRemoved).map {
            if (it.isSuccessful) {
                it.isSuccessful
            } else {
                throw Exception(ConstantErrors.ERROR_REMOVE_EXERCISE)
            }
        }
    }

    override fun retrieveSongsListByUserId(
        idUser: String,
        instrumentModeValue: Int
    ): Observable<List<SongRemoteEntity>> {
        return apiService.retrieveSongsListByUserId(idUser, instrumentModeValue).map {
            if (it.body() != null) {
                it.body()
            } else {
                throw Exception(ConstantErrors.ERROR_RETRIEVE_SONGS)
            }
        }
    }

    override fun retrieveSongFromId(idSong: String): Observable<SongRemoteEntity> {
        return apiService.retrieveSongFromId(idSong).map {
            if (it.body() != null) {
                it.body()
            } else {
                throw Exception(ConstantErrors.ERROR_RETRIEVE_SONG)
            }
        }
    }

    override fun createSong(songRemoteEntity: SongRemoteEntity): Observable<String> {
        return apiService.createSong(songRemoteEntity).map {
            if (it.isSuccessful && it.body() != null) {
                (it.body() as SongResponseRemoteEntity).getCreatedId()
            } else {
                throw Exception(ConstantErrors.ERROR_CREATION_SONG)
            }
        }
    }

    override fun updateSong(songRemoteEntity: SongRemoteEntity): Observable<Boolean> {
        return apiService.updateSong(songRemoteEntity.idSong, songRemoteEntity).map {
            if (it.isSuccessful) {
                it.isSuccessful
            } else {
                throw Exception(ConstantErrors.ERROR_UPDATE_SONG)
            }
        }
    }

    override fun removeSong(idSong: String): Observable<Boolean> {
        return apiService.removeSong(idSong).map {
            if (it.isSuccessful) {
                it.isSuccessful
            } else {
                throw Exception(ConstantErrors.ERROR_REMOVE_SONG)
            }
        }
    }

    override fun sendScoreFeedback(
        scoreFeedbackRemoteEntity: ScoreFeedbackRemoteEntity,
        idSong: String
    ): Observable<Boolean> {
        return apiService.sendScoreFeedback(scoreFeedbackRemoteEntity, idSong).map {
            if (it.isSuccessful) {
                it.isSuccessful
            } else {
                throw Exception(ConstantErrors.ERROR_SEND_FEEDBACK_SONG)
            }
        }
    }

    override fun retrieveSongScoreHistoric(idSong: String): Observable<List<ScoreRemoteEntity>> {
        return apiService.retrieveSongScoreHistoric(idSong).map {
            if (it.isSuccessful && it.body() != null) {
                it.body()
            } else {
                throw Exception(ConstantErrors.ERROR_RETRIEVE_SCORE_SONG_HISTORIC)
            }
        }
    }
}