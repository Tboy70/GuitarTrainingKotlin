package thomas.guitartrainingkotlin.presentation.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.activity.UserPanelActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerFragment
import thomas.guitartrainingkotlin.presentation.fragment.other.LegalNoticesFragment
import thomas.guitartrainingkotlin.presentation.fragment.program.UserProgramCreationFragment
import thomas.guitartrainingkotlin.presentation.fragment.program.UserProgramsListFragment
import thomas.guitartrainingkotlin.presentation.fragment.song.UserSongsListFragment
import thomas.guitartrainingkotlin.presentation.fragment.user.UserSettingsFragment

@Module(includes = [BaseActivityModule::class])
abstract class UserPanelActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(userPanelActivity: UserPanelActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userProgramsListFragmentInjector(): UserProgramsListFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userSongsListFragmentInjector(): UserSongsListFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userProgramCreationFragmentInjector(): UserProgramCreationFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userSettingsFragmentInjector(): UserSettingsFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun legalNoticesFragmentInjector(): LegalNoticesFragment
}