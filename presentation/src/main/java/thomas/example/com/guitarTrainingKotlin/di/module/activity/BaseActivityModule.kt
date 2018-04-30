package thomas.example.com.guitarTrainingKotlin.di.module.activity

import android.support.v4.app.FragmentManager
import dagger.Module
import dagger.Provides
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.listener.BaseNavigatorListener
import thomas.example.com.guitarTrainingKotlin.di.PerActivity

/**
 * The BaseActivity is provided by children Modules (modules which include this one)
 *  -> We created a @Provides method in those module to provide an instance of BaseActivity
 *  This Module will be theoretically included in ALL children Activities Module.
 */
@Module
class BaseActivityModule {

    @PerActivity
    @Provides
    fun provideSupportFragmentManager(activity: BaseActivity): FragmentManager = activity.supportFragmentManager

    @PerActivity
    @Provides
    fun provideBaseNavigatorListener(activity: BaseActivity): BaseNavigatorListener = activity
}