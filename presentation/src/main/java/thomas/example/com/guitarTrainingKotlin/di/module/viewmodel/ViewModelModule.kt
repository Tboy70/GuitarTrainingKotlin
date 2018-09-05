package thomas.example.com.guitarTrainingKotlin.di.module.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import thomas.example.com.guitarTrainingKotlin.di.ViewModelKey
import thomas.example.com.guitarTrainingKotlin.viewmodel.StartViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.exercise.ExerciseModeViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.exercise.ExerciseScaleViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.login.LoginHomeViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.program.ProgramViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.program.UserPanelViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.program.UserProgramCreationViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.program.UserProgramUpdateViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserProgramDetailsViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserProgramsListViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserSongsListViewModel

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
    @ViewModelKey(UserPanelViewModel::class)
    abstract fun bindUserPanelViewModel(userPanelViewModel: UserPanelViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserSongsListViewModel::class)
    abstract fun bindUserSongsListViewModel(userSongsListViewModel: UserSongsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProgramDetailsViewModel::class)
    abstract fun bindUserProgramDetailsViewModel(userProgramDetailsViewModel: UserProgramDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseScaleViewModel::class)
    abstract fun bindExerciseScaleViewModel(exerciseScaleViewModel: ExerciseScaleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseModeViewModel::class)
    abstract fun bindExerciseModeViewModel(exerciseModeViewModel: ExerciseModeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProgramCreationViewModel::class)
    abstract fun bindUserProgramCreationViewModel(userProgramCreationViewModel: UserProgramCreationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProgramUpdateViewModel::class)
    abstract fun bindUserProgramUpdateViewModel(userProgramUpdateViewModel: UserProgramUpdateViewModel): ViewModel
}