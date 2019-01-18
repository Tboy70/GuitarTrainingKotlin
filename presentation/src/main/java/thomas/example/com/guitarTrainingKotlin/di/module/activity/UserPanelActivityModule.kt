package thomas.example.com.guitarTrainingKotlin.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerFragment
import thomas.example.com.guitarTrainingKotlin.fragment.other.LegalNoticesFragment
import thomas.example.com.guitarTrainingKotlin.fragment.program.UserProgramCreationFragment
import thomas.example.com.guitarTrainingKotlin.fragment.program.UserProgramsListFragment
import thomas.example.com.guitarTrainingKotlin.fragment.song.UserSongCreationFragment
import thomas.example.com.guitarTrainingKotlin.fragment.song.UserSongsListFragment
import thomas.example.com.guitarTrainingKotlin.fragment.user.UserSettingsFragment

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
    abstract fun userSongCreationFragmentInjector(): UserSongCreationFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userSettingsFragmentInjector(): UserSettingsFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun legalNoticesFragmentInjector(): LegalNoticesFragment
}