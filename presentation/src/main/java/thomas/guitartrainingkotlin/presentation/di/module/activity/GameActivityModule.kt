package thomas.guitartrainingkotlin.presentation.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.activity.GameActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerFragment
import thomas.guitartrainingkotlin.presentation.fragment.game.GameListFragment
import thomas.guitartrainingkotlin.presentation.fragment.game.IntervalGameFragment
import thomas.guitartrainingkotlin.presentation.fragment.game.ScaleGameFragment

@Module(includes = [BaseActivityModule::class])
abstract class GameActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(gameActivity: GameActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector
    abstract fun gameListFragmentInjector(): GameListFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun intervalGameFragmentInjector(): IntervalGameFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun scaleGameFragmentInjector(): ScaleGameFragment
}