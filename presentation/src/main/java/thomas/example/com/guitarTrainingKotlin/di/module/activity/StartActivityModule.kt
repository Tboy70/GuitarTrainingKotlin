package thomas.example.com.guitarTrainingKotlin.di.module.activity

import dagger.Binds
import dagger.Module
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.StartActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity

/** Every application module needs to includes our BaseActivityModule in order to provide a BaseActivity instance */
@Module(includes = [BaseActivityModule::class])
abstract class StartActivityModule {

    /**
     * We need to tell Dagger how to find a BaseActivity because it can't provide it
     * -> because it has never been added into the dependency graph by a generated Subcomponent because we didn't create one for BaseActivity
     */
    @PerActivity
    @Binds
    abstract fun bindBaseActivity(startActivity: StartActivity): BaseActivity
}