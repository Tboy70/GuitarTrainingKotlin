package thomas.example.com.guitarTrainingKotlin.di.module.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import thomas.example.com.guitarTrainingKotlin.di.ViewModelKey
import thomas.example.com.guitarTrainingKotlin.viewmodel.LoginHomeViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.StartViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.UserProgramListViewModel

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
    @ViewModelKey(UserProgramListViewModel::class)
    abstract fun bindUserProgramListViewModel(userProgramListViewModel: UserProgramListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}