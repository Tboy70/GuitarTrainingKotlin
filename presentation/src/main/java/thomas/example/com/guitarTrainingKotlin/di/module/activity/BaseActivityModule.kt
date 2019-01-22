package thomas.example.com.guitarTrainingKotlin.di.module.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.listener.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerActivity

@Module
class BaseActivityModule {

    @Provides
    @PerActivity
    fun provideSupportFragmentManager(activity: BaseActivity): FragmentManager = activity.supportFragmentManager

    @Provides
    @PerActivity
    fun appCompatActivity(baseActivity: BaseActivity) = baseActivity as AppCompatActivity

    @Provides
    @PerActivity
    fun errorRendererComponent(errorRendererComponent: ErrorRendererComponentImpl): ErrorRendererComponent = errorRendererComponent

    @Provides
    @PerActivity
    fun materialDialogComponent(materialDialogComponent: MaterialDialogComponentImpl): MaterialDialogComponent = materialDialogComponent
}