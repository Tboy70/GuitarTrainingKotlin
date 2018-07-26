package thomas.example.com.guitarTrainingKotlin.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.LoginActivity
import thomas.example.com.guitarTrainingKotlin.activity.StartActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.module.activity.LoginActivityModule
import thomas.example.com.guitarTrainingKotlin.di.module.activity.StartActivityModule
import thomas.example.com.guitarTrainingKotlin.di.module.activity.UserPanelActivityModule
import thomas.example.com.guitarTrainingKotlin.di.module.activity.UserProgramActivityModule

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
}