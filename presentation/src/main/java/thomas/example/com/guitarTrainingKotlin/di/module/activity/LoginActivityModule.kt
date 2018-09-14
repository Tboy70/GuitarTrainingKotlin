package thomas.example.com.guitarTrainingKotlin.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.LoginActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.PerFragment
import thomas.example.com.guitarTrainingKotlin.fragment.login.CreateAccountFragment
import thomas.example.com.guitarTrainingKotlin.fragment.login.LoginHomeFragment

@Module(includes = [BaseActivityModule::class])
abstract class LoginActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(loginActivity: LoginActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector
    abstract fun loginHomeFragmentInjector(): LoginHomeFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun createAccountFragmentInjector(): CreateAccountFragment

}