package thomas.guitartrainingkotlin.data.di.module

import dagger.Binds
import dagger.Module
import thomas.guitartrainingkotlin.data.manager.api.ApiManager
import thomas.guitartrainingkotlin.data.manager.api.ApiManagerImpl
import thomas.guitartrainingkotlin.data.manager.db.DBManager
import thomas.guitartrainingkotlin.data.manager.db.DBManagerImpl
import thomas.guitartrainingkotlin.data.manager.sharedprefs.SharedPrefsManager
import thomas.guitartrainingkotlin.data.manager.sharedprefs.SharedPrefsManagerImpl
import javax.inject.Singleton

@Module
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun sharedPrefsManager(sharedPrefsManagerImpl: SharedPrefsManagerImpl): SharedPrefsManager

    @Binds
    @Singleton
    abstract fun apiManager(apiManagerImpl: ApiManagerImpl): ApiManager

    @Binds
    @Singleton
    abstract fun dbManager(dbManagerImpl: DBManagerImpl): DBManager
}