package thomas.example.com.guitarTrainingKotlin.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import thomas.example.com.data.executor.JobExecutor
import thomas.example.com.data.repository.UserRepositoryImpl
import thomas.example.com.executor.PostExecutionThread
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.guitarTrainingKotlin.executor.UIThread
import thomas.example.com.repository.UserRepository
import javax.inject.Singleton

@Module
abstract class ApplicationModule {

    @Singleton
    @Binds
    abstract fun provideContext(application: Application): Context

    @Singleton
    @Binds
    abstract fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

    @Singleton
    @Binds
    abstract fun providePostExecutionThread(uiThread: UIThread): PostExecutionThread

    @Singleton
    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository

}