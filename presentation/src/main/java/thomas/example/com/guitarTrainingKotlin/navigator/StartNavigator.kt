package thomas.example.com.guitarTrainingKotlin.navigator

import android.support.v4.app.FragmentManager
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import javax.inject.Inject

@PerActivity
class StartNavigator @Inject constructor(private val baseActivity: BaseActivity, private val fragmentManager: FragmentManager)