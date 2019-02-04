package thomas.example.com.guitarTrainingKotlin.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.SongCreationActivity
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerFragment
import thomas.example.com.guitarTrainingKotlin.fragment.song.UserSongCreationFragment

@Module(includes = [BaseActivityModule::class])
abstract class SongCreationActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(songCreationActivity: SongCreationActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userSongCreationFragmentInjector(): UserSongCreationFragment

}