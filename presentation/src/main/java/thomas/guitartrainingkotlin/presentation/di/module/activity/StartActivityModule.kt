package thomas.guitartrainingkotlin.presentation.di.module.activity

import dagger.Binds
import dagger.Module
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.activity.StartActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity

@Module(includes = [BaseActivityModule::class])
abstract class StartActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(startActivity: StartActivity): BaseActivity
}