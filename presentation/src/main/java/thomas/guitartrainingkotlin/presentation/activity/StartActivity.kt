package thomas.guitartrainingkotlin.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.viewmodel.StartActivityViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.factory.ViewModelFactory
import javax.inject.Inject

class StartActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var startActivityViewModel: StartActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(StartActivityViewModel::class.java)

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