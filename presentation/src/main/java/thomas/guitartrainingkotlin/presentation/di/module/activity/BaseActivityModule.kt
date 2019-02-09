package thomas.guitartrainingkotlin.presentation.di.module.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.component.DialogComponentImpl
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.component.ExercisesUIComponentImpl
import thomas.guitartrainingkotlin.presentation.component.SnackbarComponentImpl
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.ErrorRendererComponent
import thomas.guitartrainingkotlin.presentation.component.listener.ExercisesUIComponent
import thomas.guitartrainingkotlin.presentation.component.listener.SnackbarComponent
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity

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
    fun errorRendererComponent(errorRendererComponent: ErrorRendererComponentImpl): ErrorRendererComponent =
        errorRendererComponent

    @Provides
    @PerActivity
    fun exerciseUIComponent(exercisesUIComponent: ExercisesUIComponentImpl): ExercisesUIComponent =
        exercisesUIComponent


    @Provides
    @PerActivity
    fun dialogComponent(dialogComponent: DialogComponentImpl): DialogComponent =
        dialogComponent

    @Provides
    @PerActivity
    fun snackbarComponent(snackbarComponent: SnackbarComponentImpl): SnackbarComponent = snackbarComponent
}