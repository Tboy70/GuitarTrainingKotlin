package thomas.example.com.guitarTrainingKotlin.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.StartActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.module.activity.StartActivityModule

/**
 * Here will be listed all our Activities which need to be injected with some objects (Presenters, Navigators...)
 */
@Module
abstract class ActivityInjectorModule {

    /**
     * This will generate a SubComponent and a Subcomponent.Builder for injecting StartActivity instances
     * It will also provide the instance of StartActivity which has been injected
     * We add StartActivityModule because in StartActivityModule we define :
     *  - FragmentInjectors for this activity
     *  - Dependencies needed for other objects depending on this activity
     */
    @PerActivity
    @ContributesAndroidInjector(modules = [StartActivityModule::class])
    abstract fun startActivityInjector(): StartActivity

}