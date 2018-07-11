package thomas.example.com.guitarTrainingKotlin.di.module.activity

import dagger.Binds
import dagger.Module
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.LoginActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity

@Module(includes = [BaseActivityModule::class])
abstract class LoginActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(loginActivity: LoginActivity): BaseActivity

}