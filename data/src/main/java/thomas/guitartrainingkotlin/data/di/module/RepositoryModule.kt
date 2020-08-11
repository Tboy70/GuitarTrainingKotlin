package thomas.guitartrainingkotlin.data.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import thomas.guitartrainingkotlin.data.repository.ProgramDataRepository
import thomas.guitartrainingkotlin.data.repository.SongDataRepository
import thomas.guitartrainingkotlin.data.repository.UserDataRepository
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun programRepository(programDataRepository: ProgramDataRepository): ProgramRepository

    @Binds
    @Singleton
    abstract fun songRepository(songDataRepository: SongDataRepository): SongRepository

    @Binds
    @Singleton
    abstract fun userRepository(userDataRepository: UserDataRepository): UserRepository
}
