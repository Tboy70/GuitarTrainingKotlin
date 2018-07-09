package thomas.example.com.guitarTrainingKotlin.di.component

import dagger.Component
import thomas.example.com.guitarTrainingKotlin.di.module.viewmodel.ViewModelModule
import thomas.example.com.guitarTrainingKotlin.viewmodel.StartViewModel

@Component( modules = [(ViewModelModule::class)])
interface ViewModelComponent {
    // inject your view models
    fun inject(startViewModel: StartViewModel)

}