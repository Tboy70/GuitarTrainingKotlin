package thomas.example.com.guitarTrainingKotlin.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import thomas.example.com.data.executor.JobExecutor
import thomas.example.com.data.module.ApiModule
import thomas.example.com.data.module.ApiModuleImpl
import thomas.example.com.data.repository.ProgramRepositoryImpl
import thomas.example.com.data.repository.SongRepositoryImpl
import thomas.example.com.data.repository.UserRepositoryImpl
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.repository.ProgramRepository
import thomas.example.com.repository.SongRepository
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
    abstract fun provideAPIModule(apiModuleImpl: ApiModuleImpl): ApiModule

    @Singleton
    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun provideProgramRepository(programRepositoryImpl: ProgramRepositoryImpl): ProgramRepository

    @Singleton
    @Binds
    abstract fun provideSongRepository(songRepositoryImpl: SongRepositoryImpl): SongRepository

}