package thomas.guitartrainingkotlin.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import thomas.guitartrainingkotlin.data.di.module.DataModule
import thomas.guitartrainingkotlin.data.di.module.ManagerModule
import thomas.guitartrainingkotlin.domain.executor.ThreadExecutor
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        ManagerModule::class
    ]
)
interface DataComponent {

    //We expose our Repositories to dependent scopes
    fun userRepository(): UserRepository

    fun programRepository(): ProgramRepository

    fun songRepository(): SongRepository

    fun threadExecutor(): ThreadExecutor

    @Component.Builder
    abstract class Builder {
        @BindsInstance
        abstract fun context(context: Context): Builder

        abstract fun build(): DataComponent
    }

}