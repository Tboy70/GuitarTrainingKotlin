package thomas.guitartrainingkotlin.presentation.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.activity.ProgramActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerFragment
import thomas.guitartrainingkotlin.presentation.fragment.exercise.*
import thomas.guitartrainingkotlin.presentation.fragment.program.EndProgramFragment
import thomas.guitartrainingkotlin.presentation.fragment.program.IntroProgramFragment

@Module(includes = [BaseActivityModule::class])
abstract class ProgramActivityModule {
    @PerActivity
    @Binds
    abstract fun bindBaseActivity(programActivity: ProgramActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector
    abstract fun exerciseScaleFragmentInjector(): ExerciseScaleFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun exerciseModeFragmentInjector(): ExerciseModeFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun exercisePullOffHammerOnFragmentInjector(): ExercisePullOffHammerOnFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun exerciseBendSlideFragmentInjector(): ExerciseBendSlideFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun exerciseBackForthFragmentInjector(): ExerciseBackForthFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun exerciseSkipStringFragmentInjector(): ExerciseSkipStringFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun exercisePalmMuteFragmentInjector(): ExercisePalmMuteFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun exerciseTappingFragmentInjector(): ExerciseTappingFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun exerciseSweepPickingFragmentInjector(): ExerciseSweepPickingFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun exerciseSpeedFragmentInjector(): ExerciseSpeedFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun exerciseSlapFragmentInjector(): ExerciseSlapFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun exerciseThreeFingersFragmentInjector(): ExerciseThreeFingersFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun introProgramFragmentInjector(): IntroProgramFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun endProgramFragmentInjector(): EndProgramFragment
}