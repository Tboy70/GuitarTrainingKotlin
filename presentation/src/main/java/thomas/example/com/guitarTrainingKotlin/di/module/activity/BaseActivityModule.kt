package thomas.example.com.guitarTrainingKotlin.di.module.activity

import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity

@Module
class BaseActivityModule {

    @PerActivity
    @Provides
    fun provideSupportFragmentManager(activity: BaseActivity): FragmentManager = activity.supportFragmentManager
}