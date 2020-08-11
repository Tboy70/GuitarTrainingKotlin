package thomas.guitartrainingkotlin.presentation.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity

@Module
@InstallIn(ActivityComponent::class)
object BaseActivityModule {

    @ActivityScoped
    @Provides
    fun provideBaseActivity(activity: Activity): BaseActivity {
        check(activity is BaseActivity) { "Every Activity is expected to extend BaseActivity" }
        return activity
    }
}