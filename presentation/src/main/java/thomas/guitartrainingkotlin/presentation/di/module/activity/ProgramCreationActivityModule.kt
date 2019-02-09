package thomas.guitartrainingkotlin.presentation.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.activity.ProgramCreationActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import thomas.guitartrainingkotlin.presentation.di.annotation.PerFragment
import thomas.guitartrainingkotlin.presentation.fragment.program.UserProgramCreationFragment

@Module(includes = [BaseActivityModule::class])
abstract class ProgramCreationActivityModule {

    @PerActivity
    @Binds
    abstract fun bindBaseActivity(programCreationActivity: ProgramCreationActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector
    abstract fun userProgramCreationFragmentInjector(): UserProgramCreationFragment

}