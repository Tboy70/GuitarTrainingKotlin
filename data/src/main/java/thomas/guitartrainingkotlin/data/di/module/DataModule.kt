package thomas.guitartrainingkotlin.data.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import thomas.guitartrainingkotlin.data.executor.JobExecutor
import thomas.guitartrainingkotlin.data.repository.ProgramDataRepository
import thomas.guitartrainingkotlin.data.repository.SongDataRepository
import thomas.guitartrainingkotlin.data.repository.UserDataRepository
import thomas.guitartrainingkotlin.domain.executor.ThreadExecutor
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import thomas.guitartrainingkotlin.domain.repository.UserRepository
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