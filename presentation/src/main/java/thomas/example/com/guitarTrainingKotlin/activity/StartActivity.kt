package thomas.example.com.guitarTrainingKotlin.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.viewmodel.StartViewModel
import thomas.example.com.interactor.sharedprefs.GetIdInSharedPrefs
import javax.inject.Inject

class StartActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var startViewModel: StartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startViewModel = ViewModelProviders.of(this, viewModelFactory).get(StartViewModel::class.java)

        /** Check the user ID in shared prefs to know what should be the right activity to launch. **/
        startViewModel.idUserPref.observe(this, Observer<String> {
            if (it.equals(GetIdInSharedPrefs.ID_USER_DEFAULT)) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, UserPanelActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        getUserPrefIsConnected()
    }

    private fun getUserPrefIsConnected() {
        startViewModel.getUserPrefIsConnected()
    }
}