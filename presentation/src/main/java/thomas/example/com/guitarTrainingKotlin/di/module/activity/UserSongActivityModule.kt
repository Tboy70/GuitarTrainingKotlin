package thomas.example.com.guitarTrainingKotlin.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserSongActivity
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerFragment
import thomas.example.com.guitarTrainingKotlin.fragment.song.UserSongDetailsFragment
import thomas.example.com.guitarTrainingKotlin.fragment.song.UserSongUpdateFragment

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