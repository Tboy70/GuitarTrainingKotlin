package thomas.guitartrainingkotlin.presentation.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import thomas.guitartrainingkotlin.presentation.di.annotation.ViewModelKey
import thomas.guitartrainingkotlin.presentation.viewmodel.StartActivityViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.exercise.ExerciseModeViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.exercise.ExerciseScaleViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.login.CreateAccountViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.login.LoginHomeViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.other.LegalNoticesViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.program.ProgramViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.program.UserPanelViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.program.UserProgramCreationViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.program.UserProgramUpdateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.song.UserSongCreationViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.song.UserSongUpdateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.user.*

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StartActivityViewModel::class)
    abstract fun bindStartActivityViewModel(startActivityViewModel: StartActivityViewModel): ViewModel

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
    @ViewModelKey(UserSongCreationViewModel::class)
    abstract fun bindUserSongCreationViewModel(userSongCreationViewModel: UserSongCreationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProgramUpdateViewModel::class)
    abstract fun bindUserProgramUpdateViewModel(userProgramUpdateViewModel: UserProgramUpdateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateAccountViewModel::class)
    abstract fun bindCreateAccountViewModel(createAccountViewModel: CreateAccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserSettingsViewModel::class)
    abstract fun bindUserSettingsViewModel(userSettingsViewModel: UserSettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LegalNoticesViewModel::class)
    abstract fun bindLegalNoticesViewModel(legalNoticesViewModel: LegalNoticesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserSongDetailsViewModel::class)
    abstract fun bindUserSongDetailsViewModel(userSongDetailsViewModel: UserSongDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserSongUpdateViewModel::class)
    abstract fun bindUserSongUpdateViewModel(userSongUpdateViewModel: UserSongUpdateViewModel): ViewModel
}