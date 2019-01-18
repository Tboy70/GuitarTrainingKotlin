package thomas.example.com.guitarTrainingKotlin.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerApplication

@Module
class ApplicationModule {

    @Provides
    @PerApplication
    fun context(application: Application): Context = application
}