package thomas.example.com.guitarTrainingKotlin.di.module.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import thomas.example.com.guitarTrainingKotlin.di.ViewModelKey
import thomas.example.com.guitarTrainingKotlin.viewmodel.LoginHomeViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.StartViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.UserProgramsListViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.UserSongsListViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    abstract fun bindStartViewModel(startViewModel: StartViewModel): ViewModel

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
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}