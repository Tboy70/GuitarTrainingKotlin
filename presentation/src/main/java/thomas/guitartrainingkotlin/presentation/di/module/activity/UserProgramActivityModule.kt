package thomas.guitartrainingkotlin.presentation.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.activity.UserProgramActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerFragment
import thomas.guitartrainingkotlin.presentation.fragment.program.UserProgramDetailsFragment
import thomas.guitartrainingkotlin.presentation.fragment.program.UserProgramUpdateFragment

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