package thomas.guitartrainingkotlin.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.viewmodel.StartActivityViewModel

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class StartActivity : BaseActivity() {

    private val startActivityViewModel by viewModels<StartActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        initiateViewModelObserver()
    }

    private fun initiateViewModelObserver() {
        /**
         * observe cause the posted value can be null (if first app launch)
         * observeSafe --> Only if the posted value can not be null
         */
        startActivityViewModel.retrievedUserIdLiveEvent.observe(this) { userId ->
            userId?.let {
                startActivity(Intent(this, UserPanelActivity::class.java).apply {
                    putExtra(ConstValues.USER_ID, it)
                })

            } ?: startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}