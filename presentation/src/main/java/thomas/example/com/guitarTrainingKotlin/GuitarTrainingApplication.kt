package thomas.example.com.guitarTrainingKotlin

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import thomas.example.com.guitarTrainingKotlin.di.component.DaggerApplicationComponent
import javax.inject.Inject

/**
 * Application class.
 */
class GuitarTrainingApplication : Application(), HasActivityInjector {

    @Inject
    private
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder().application(this).build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}