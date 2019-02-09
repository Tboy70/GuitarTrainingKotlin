package thomas.guitartrainingkotlin.presentation.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.activity.SongCreationActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerFragment
import thomas.guitartrainingkotlin.presentation.fragment.song.UserSongCreationFragment

@Module(includes = [BaseActivityModule::class])
abstract class SongCreationActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(songCreationActivity: SongCreationActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userSongCreationFragmentInjector(): UserSongCreationFragment

}