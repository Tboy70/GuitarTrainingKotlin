package thomas.guitartrainingkotlin.presentation.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.activity.UserSongActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerFragment
import thomas.guitartrainingkotlin.presentation.fragment.song.UserSongDetailsFragment
import thomas.guitartrainingkotlin.presentation.fragment.song.UserSongUpdateFragment

@Module(includes = [BaseActivityModule::class])
abstract class UserSongActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(userSongActivity: UserSongActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userSongDetailsFragmentInjector(): UserSongDetailsFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userSongUpdateFragmentInjector(): UserSongUpdateFragment

}