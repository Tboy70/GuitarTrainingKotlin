package thomas.example.com.data.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import thomas.example.com.data.executor.JobExecutor
import thomas.example.com.data.repository.ProgramDataRepository
import thomas.example.com.data.repository.SongDataRepository
import thomas.example.com.data.repository.UserDataRepository
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.repository.ProgramRepository
import thomas.example.com.repository.SongRepository
import thomas.example.com.repository.UserRepository
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun jobExecutor(jobExecutor: JobExecutor): ThreadExecutor = jobExecutor

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().create()


    @Provides
    @Singleton
    fun userRepository(userDataRepository: UserDataRepository): UserRepository = userDataRepository

    @Provides
    @Singleton
    fun programRepository(programDataRepository: ProgramDataRepository): ProgramRepository = programDataRepository

    @Provides
    @Singleton
    fun songRepository(songDataRepository: SongDataRepository): SongRepository = songDataRepository
}