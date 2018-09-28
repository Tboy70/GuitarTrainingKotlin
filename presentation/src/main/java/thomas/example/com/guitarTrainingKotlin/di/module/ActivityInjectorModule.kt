package thomas.example.com.guitarTrainingKotlin.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.*
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.module.activity.*

@Module
abstract class ActivityInjectorModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [StartActivityModule::class])
    abstract fun startActivityInjector(): StartActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun loginActivityInjector(): LoginActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [UserPanelActivityModule::class])
    abstract fun userPanelActivityInjector(): UserPanelActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [UserProgramActivityModule::class])
    abstract fun userProgramActivityInjector(): UserProgramActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [UserSongActivityModule::class])
    abstract fun userSongActivityInjector(): UserSongActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [ProgramActivityModule::class])
    abstract fun programActivityInjector(): ProgramActivity
}