package thomas.example.com.guitarTrainingKotlin.di.module.activity

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.activity.ProgramActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.guitarTrainingKotlin.di.PerFragment
import thomas.example.com.guitarTrainingKotlin.fragment.program.IntroProgramFragment

@Module(includes = [BaseActivityModule::class])
abstract class ProgramActivityModule {
    @PerActivity
    @Binds
    abstract fun bindBaseActivity(programActivity: ProgramActivity): BaseActivity

    @PerFragment
    @ContributesAndroidInjector
    abstract fun introProgramFragmentInjector(): IntroProgramFragment
}