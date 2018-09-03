package thomas.example.com.data.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import thomas.example.com.data.entity.remote.ExerciseRemoteEntity
import thomas.example.com.data.entity.remote.ProgramRemoteEntity
import thomas.example.com.data.entity.remote.UserRemoteEntity
import thomas.example.com.data.entity.remote.program.ProgramResponseRemoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiModuleImpl @Inject constructor() : ApiModule {

    private var apiService: APIServiceInterface

    companion object {
        //        const val BASE_URL = "http://192.168.1.30/guitar_api/public/" // BOX
        const val BASE_URL = "http://thomasboy.fr/guitar_api/public/" // BOX
        //        val BASE_URL = "http://192.168.43.235/guitar_api/public/" // 4G
    }

    init {
        val gson: Gson = GsonBuilder().create()
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
        return apiService.connectUser(userRemoteEntity)
    }

    override fun retrieveProgramsListByUserId(idUser: String): Observable<List<ProgramRemoteEntity>> {
        return apiService.retrieveProgramsListByUserId(idUser)
    }

    override fun retrieveProgramFromId(idProgram: String): Observable<ProgramRemoteEntity> {
        return apiService.retrieveProgram(idProgram)
    }

    override fun createProgram(programRemoteEntity: ProgramRemoteEntity): Observable<String> {
        return apiService.createProgram(programRemoteEntity).map {
            if (it.isSuccessful && it.body() != null) {
                (it.body() as ProgramResponseRemoteEntity).getCreatedId()
            } else {
                null
            }
        }
    }

    override fun createExercise(listRemoteEntities: List<ExerciseRemoteEntity>): Observable<Boolean> {
        return apiService.createExercise(listRemoteEntities).map {
            it.isSuccessful
        }
    }
}


interface APIServiceInterface {

    @POST("connect")
    fun connectUser(@Body userRemoteEntity: UserRemoteEntity): Observable<UserRemoteEntity>

    @GET("programs/{idUser}")
    fun retrieveProgramsListByUserId(@Path("idUser") idUser: String): Observable<List<ProgramRemoteEntity>>

    @GET("program/{idProgram}")
    fun retrieveProgram(@Path("idProgram") idProgram: String): Observable<ProgramRemoteEntity>

    @POST("program")
    fun createProgram(@Body programRemoteEntity: ProgramRemoteEntity): Observable<Response<ProgramResponseRemoteEntity>>

    @POST("exercise")
    fun createExercise(@Body exerciseRemoteEntity: List<ExerciseRemoteEntity>): Observable<Response<Void>>
}
