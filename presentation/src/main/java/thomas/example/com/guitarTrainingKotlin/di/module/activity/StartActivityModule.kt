package thomas.example.com.guitarTrainingKotlin.di.module.activity

import dagger.Binds
import dagger.Module
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.StartActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity

@Module(includes = [BaseActivityModule::class])
abstract class StartActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(startActivity: StartActivity): BaseActivity
}