package thomas.example.com.guitarTrainingKotlin.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.PerFragment
import thomas.example.com.guitarTrainingKotlin.fragment.user.UserProgramListFragment

@Module(includes = [BaseActivityModule::class])
abstract class UserPanelActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(userPanelActivity: UserPanelActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userProgramListFragmentInjector(): UserProgramListFragment
}