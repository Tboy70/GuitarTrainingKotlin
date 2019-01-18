package thomas.example.com.guitarTrainingKotlin.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerFragment
import thomas.example.com.guitarTrainingKotlin.fragment.program.UserProgramDetailsFragment
import thomas.example.com.guitarTrainingKotlin.fragment.program.UserProgramUpdateFragment

@Module(includes = [BaseActivityModule::class])
abstract class UserProgramActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(userProgramActivity: UserProgramActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userProgramDetailsFragmentInjector(): UserProgramDetailsFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userProgramUpdateFragmentInjector(): UserProgramUpdateFragment
}