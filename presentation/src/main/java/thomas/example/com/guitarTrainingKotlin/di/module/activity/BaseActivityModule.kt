package thomas.example.com.guitarTrainingKotlin.di.module.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerActivity

@Module
class BaseActivityModule {

    @Provides
    @PerActivity
    fun provideSupportFragmentManager(activity: BaseActivity): FragmentManager = activity.supportFragmentManager

    @Provides
    @PerActivity
    fun appCompatActivity(baseActivity: BaseActivity) = baseActivity as AppCompatActivity
}