package thomas.guitartrainingkotlin.presentation.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.guitartrainingkotlin.presentation.activity.*
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import thomas.guitartrainingkotlin.presentation.di.module.activity.*

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
    @ContributesAndroidInjector(modules = [SongCreationActivityModule::class])
    abstract fun songCreationActivityInjector(): SongCreationActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [ProgramActivityModule::class])
    abstract fun programActivityInjector(): ProgramActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [ProgramCreationActivityModule::class])
    abstract fun programCreationActivityInjector(): ProgramCreationActivity
}