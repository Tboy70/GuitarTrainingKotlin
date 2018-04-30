package thomas.example.com.guitarTrainingKotlin.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import thomas.example.com.data.executor.JobExecutor
import thomas.example.com.executor.PostExecutionThread
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.guitarTrainingKotlin.GuitarTrainingApplication
import thomas.example.com.guitarTrainingKotlin.executor.UIThread
import javax.inject.Singleton

@Module
abstract class ApplicationModule(private var application: GuitarTrainingApplication) {

    @Singleton
    @Binds
    abstract fun provideApplicationContext(application: Application): Context

    @Singleton
    @Binds
    abstract fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

    @Singleton
    @Binds
    abstract fun providePostExecutionThread(uiThread: UIThread): PostExecutionThread

}