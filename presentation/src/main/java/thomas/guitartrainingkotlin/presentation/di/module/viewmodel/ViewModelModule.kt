package thomas.guitartrainingkotlin.presentation.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import thomas.guitartrainingkotlin.presentation.di.annotation.ViewModelKey
import thomas.guitartrainingkotlin.presentation.viewmodel.StartActivityViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.exercise.*
import thomas.guitartrainingkotlin.presentation.viewmodel.game.GameListViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.game.IntervalGameViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.game.ReversedIntervalGameViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.game.ScaleGameViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.login.CreateAccountViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.login.ForgotPasswordViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.login.LoginHomeViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.other.LegalNoticesViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.program.*
import thomas.guitartrainingkotlin.presentation.viewmodel.shared.ProgramSharedViewModel
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
    @ViewModelKey(ExerciseBackForthViewModel::class)
    abstract fun bindExerciseBackForthViewModel(exerciseBackForthViewModel: ExerciseBackForthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseBendSlideViewModel::class)
    abstract fun bindExerciseBendSlideViewModel(exerciseBendSlideViewModel: ExerciseBendSlideViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExercisePalmMuteViewModel::class)
    abstract fun bindExercisePalmMuteViewModel(exercisePalmMuteViewModel: ExercisePalmMuteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExercisePullOffHammerOnViewModel::class)
    abstract fun bindExercisePullOffHammerOnViewModel(exercisePullOffHammerOnViewModel: ExercisePullOffHammerOnViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseSkipStringViewModel::class)
    abstract fun bindExerciseSkipStringViewModel(exerciseSkipStringViewModel: ExerciseSkipStringViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseSlapViewModel::class)
    abstract fun bindExerciseSlapViewModel(exerciseSlapViewModel: ExerciseSlapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseSpeedViewModel::class)
    abstract fun bindExerciseSpeedViewModel(exerciseSpeedViewModel: ExerciseSpeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseSweepPickingViewModel::class)
    abstract fun bindExerciseSweepPickingViewModel(exerciseSweepPickingViewModel: ExerciseSweepPickingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseTappingViewModel::class)
    abstract fun bindExerciseTappingViewModel(exerciseTappingViewModel: ExerciseTappingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseThreeFingersViewModel::class)
    abstract fun bindExerciseThreeFingersViewModel(exerciseThreeFingersViewModel: ExerciseThreeFingersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EndProgramViewModel::class)
    abstract fun bindEndProgramViewModel(endProgramViewModel: EndProgramViewModel): ViewModel

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
    @ViewModelKey(ForgotPasswordViewModel::class)
    abstract fun bindForgotPasswordViewModel(forgotPasswordViewModel: ForgotPasswordViewModel): ViewModel

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

    @Binds
    @IntoMap
    @ViewModelKey(GameListViewModel::class)
    abstract fun bindGameListViewModel(gameListViewModel: GameListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IntervalGameViewModel::class)
    abstract fun bindIntervalGameViewModel(intervalGameViewModel: IntervalGameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReversedIntervalGameViewModel::class)
    abstract fun bindReversedIntervalGameViewModel(reversedIntervalGameViewModel: ReversedIntervalGameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScaleGameViewModel::class)
    abstract fun bindScaleGameViewModel(scaleGameViewModel: ScaleGameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProgramSharedViewModel::class)
    abstract fun bindProgramSharedViewModel(programSharedViewModel: ProgramSharedViewModel): ViewModel
}