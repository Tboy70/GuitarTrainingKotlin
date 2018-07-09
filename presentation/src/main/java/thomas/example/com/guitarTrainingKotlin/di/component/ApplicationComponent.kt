package thomas.example.com.guitarTrainingKotlin.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import thomas.example.com.guitarTrainingKotlin.GuitarTrainingApplication
import thomas.example.com.guitarTrainingKotlin.di.module.ActivityInjectorModule
import thomas.example.com.guitarTrainingKotlin.di.module.ApplicationModule
import thomas.example.com.guitarTrainingKotlin.di.module.viewmodel.ViewModelModule
import javax.inject.Singleton

/** We only have to implement one Dagger component -
 * Other components for injecting activities will be Subcomponents automatically generated in
 * ActivityInjectorModule (one subcomponent per Activity)
 *
 * Each subcomponent will be linked to a Module (=> one module per Activity) which will allow us to inject
 * subcomponent activity's Fragments and provides Activity related objects
 * (we have a BaseActivity module which will be included in all of our ActivityModules)
 *
 * Fragments will be injected using @ContributesAndroidInjector annotation
 * -> Same as Activities, it will create a Subcomponent Fragments generally don't have to
 * Provides any related objects, so we won't have any Module for Fragments
 */

@Singleton
@Component(modules = [
    ApplicationModule::class,
    AndroidSupportInjectionModule::class,
    ActivityInjectorModule::class,
    ViewModelModule::class
])
interface ApplicationComponent {

    /** That's the only inject method we need in the application
     * -> other inject methods will be created when generating Subcomponents
     * (with @ContributesAndroidInjector annotation)
     */
    fun inject(application: GuitarTrainingApplication)

    @Component.Builder
    abstract class Builder {

        /**
         * This annotation uses the seedInstance in the background, so the given Application object will be
         * automatically Provided
         * => No need to write any Provides method for Application object
         * We can then use directly the @Binds annotation in ApplicationModule to provide a Context with a
         * provided Application instance
         **/
        @BindsInstance
        abstract fun application(application: Application): Builder

        abstract fun build(): ApplicationComponent
    }
}