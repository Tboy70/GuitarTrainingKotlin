package thomas.example.com.data.di.module

import dagger.Binds
import dagger.Module
import thomas.example.com.data.manager.ApiManager
import thomas.example.com.data.manager.ApiManagerImpl
import thomas.example.com.data.manager.SharedPrefsManager
import thomas.example.com.data.manager.SharedPrefsManagerImpl
import javax.inject.Singleton

@Module
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun sharedPrefsManager(sharedPrefsManagerImpl: SharedPrefsManagerImpl): SharedPrefsManager

    @Binds
    @Singleton
    abstract fun apiManager(apiManagerImpl: ApiManagerImpl): ApiManager
}