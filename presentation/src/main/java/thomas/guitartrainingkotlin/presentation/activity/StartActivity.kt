package thomas.guitartrainingkotlin.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.viewmodel.StartActivityViewModel
import javax.inject.Inject

@AndroidEntryPoint
class StartActivity : BaseActivity() {

    private val startActivityViewModel by viewModels<StartActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        initiateViewModelObserver()
    }

    private fun initiateViewModelObserver() {
        startActivityViewModel.retrievedUserIdLiveEvent.observeSafe(this) { userId ->
            val intent = if (userId == StartActivityViewModel.USER_ID_DEFAULT) {
                Intent(this, LoginActivity::class.java)
            } else {
                Intent(this, UserPanelActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}