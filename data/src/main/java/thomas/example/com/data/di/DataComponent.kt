package thomas.example.com.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import thomas.example.com.data.di.module.DataModule
import thomas.example.com.data.di.module.ManagerModule
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.repository.ProgramRepository
import thomas.example.com.repository.SongRepository
import thomas.example.com.repository.UserRepository
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

    fun programRepository() : ProgramRepository

    fun songRepository() : SongRepository

    fun threadExecutor(): ThreadExecutor

    @Component.Builder
    abstract class Builder {
        @BindsInstance
        abstract fun context(context: Context): Builder

        abstract fun build(): DataComponent
    }

}