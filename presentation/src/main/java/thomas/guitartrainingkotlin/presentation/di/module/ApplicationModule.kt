package thomas.guitartrainingkotlin.presentation.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import thomas.guitartrainingkotlin.presentation.di.annotation.PerApplication

@Module
class ApplicationModule {

    @Provides
    @PerApplication
    fun context(application: Application): Context = application
}