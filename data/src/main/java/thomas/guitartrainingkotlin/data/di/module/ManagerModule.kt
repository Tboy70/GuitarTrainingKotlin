package thomas.guitartrainingkotlin.data.di.module

import dagger.Binds
import dagger.Module
import thomas.guitartrainingkotlin.data.manager.ApiManager
import thomas.guitartrainingkotlin.data.manager.ApiManagerImpl
import thomas.guitartrainingkotlin.data.manager.SharedPrefsManager
import thomas.guitartrainingkotlin.data.manager.SharedPrefsManagerImpl
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