package thomas.example.com.guitarTrainingKotlin.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.PerFragment
import thomas.example.com.guitarTrainingKotlin.fragment.user.UserProgramDetailsFragment

@Module(includes = [BaseActivityModule::class])
abstract class UserProgramActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(userProgramActivity: UserProgramActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userProgramDetailsFragmentInjector(): UserProgramDetailsFragment
}