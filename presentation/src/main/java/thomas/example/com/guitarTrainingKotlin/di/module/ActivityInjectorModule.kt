package thomas.example.com.guitarTrainingKotlin.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.StartActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.module.activity.StartActivityModule

@Module
abstract class ActivityInjectorModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [StartActivityModule::class])
    abstract fun startActivityInjector(): StartActivity
}