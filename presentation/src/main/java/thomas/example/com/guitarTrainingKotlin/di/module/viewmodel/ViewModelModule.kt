package thomas.example.com.guitarTrainingKotlin.di.module.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import thomas.example.com.guitarTrainingKotlin.di.ViewModelKey
import thomas.example.com.guitarTrainingKotlin.viewmodel.*

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    abstract fun bindStartViewModel(startViewModel: StartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProgramViewModel::class)
    abstract fun bindProgramViewModel(programViewModel: ProgramViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginHomeViewModel::class)
    abstract fun bindLoginHomeViewModel(loginHomeViewModel: LoginHomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProgramsListViewModel::class)
    abstract fun bindUserProgramsListViewModel(userProgramsListViewModel: UserProgramsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserSongsListViewModel::class)
    abstract fun bindUserSongsListViewModel(userSongsListViewModel: UserSongsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProgramDetailsViewModel::class)
    abstract fun bindUserProgramDetailsViewModel(userProgramDetailsViewModel: UserProgramDetailsViewModel): ViewModel
}