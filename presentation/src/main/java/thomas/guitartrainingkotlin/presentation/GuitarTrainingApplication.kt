package thomas.guitartrainingkotlin.presentation

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import thomas.guitartrainingkotlin.data.di.DaggerDataComponent
import thomas.guitartrainingkotlin.presentation.di.component.DaggerApplicationComponent
import javax.inject.Inject

/**
 * Application class.
 * Implements HasActivityInjector cause the Application class has activities.
 * If has fragment, implements also from HasFragmentInjector !
 */
class GuitarTrainingApplication : Application(), HasActivityInjector {

    /**
     * DispatchingAndroidInjector : What injects framework class (Activities, Fragments, Services ...)
     * Call with AndroidInjection.inject(this)
     */
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .application(this)
            .dataComponent(
                DaggerDataComponent.builder().context(this).build()
            )
            .build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}