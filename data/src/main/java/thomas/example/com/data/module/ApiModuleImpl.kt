package thomas.example.com.data.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import rx.schedulers.Schedulers
import thomas.example.com.data.entity.remote.UserRemoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiModuleImpl @Inject constructor() : ApiModule {

    private var apiService: APIServiceInterface

    companion object {
        const val BASE_URL = "http://192.168.1.30/guitar_api/public/" // BOX
        //        val BASE_URL = "http://192.168.43.235/guitar_api/public/" // 4G
    }

    init {
        val gson: Gson = GsonBuilder().create()
        val rxAdapter: RxJavaCallAdapterFactory = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io())
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

    interface APIServiceInterface {

        @POST("connect")
        fun connectUser(@Body userRemoteEntity: UserRemoteEntity): Observable<UserRemoteEntity>
    }
}