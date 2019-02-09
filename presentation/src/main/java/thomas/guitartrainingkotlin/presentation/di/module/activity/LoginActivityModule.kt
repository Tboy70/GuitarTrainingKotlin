package thomas.guitartrainingkotlin.presentation.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.activity.LoginActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerFragment
import thomas.guitartrainingkotlin.presentation.fragment.login.CreateAccountFragment
import thomas.guitartrainingkotlin.presentation.fragment.login.LoginHomeFragment

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