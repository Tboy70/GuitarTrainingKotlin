package thomas.example.com.guitarTrainingKotlin.di.module.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.listener.BaseNavigatorListener
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.ViewModelKey
import thomas.example.com.guitarTrainingKotlin.di.module.activity.BaseActivityModule
import thomas.example.com.guitarTrainingKotlin.viewmodel.StartViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    // Bind your View Model here
    abstract fun bindStartViewModel(startViewModel: StartViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}