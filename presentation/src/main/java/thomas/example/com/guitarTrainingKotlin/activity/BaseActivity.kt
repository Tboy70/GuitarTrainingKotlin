package thomas.example.com.guitarTrainingKotlin.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import thomas.example.com.guitarTrainingKotlin.activity.listener.BaseNavigatorListener
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector, BaseNavigatorListener {

//    private lateinit var activityComponent: ActivityComponent
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initializeInjector()
//    }
//
//    fun getActivityComponent(): ActivityComponent {
//        return activityComponent
//    }
//
//    fun getApplicationComponent() : ApplicationComponent {
//        var app = GuitarTrainingApplication.application()
//        return app.getApplicationComponent()
//    }
//
//    fun getActivityModule() : ActivityModule {
//        return ActivityModule(this)
//    }
//
//    private fun initializeInjector() {
//        activityComponent = DaggerActivityComponent.builder()
//                .applicationComponent(getApplicationComponent())
//                .activityModule(getActivityModule())
//                .build()
//    }

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? = injector
}