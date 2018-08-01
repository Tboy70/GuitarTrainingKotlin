package thomas.example.com.guitarTrainingKotlin.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.ProgramActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.PerFragment
import thomas.example.com.guitarTrainingKotlin.fragment.exercise.*
import thomas.example.com.guitarTrainingKotlin.fragment.program.EndProgramFragment

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
    abstract fun endProgramFragmentInjector(): EndProgramFragment
}