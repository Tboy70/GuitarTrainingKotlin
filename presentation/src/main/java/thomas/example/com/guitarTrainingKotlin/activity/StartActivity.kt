package thomas.example.com.guitarTrainingKotlin.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.listener.StartNavigatorListener
import thomas.example.com.guitarTrainingKotlin.navigator.StartNavigator
import thomas.example.com.guitarTrainingKotlin.viewmodel.StartViewModel
import thomas.example.com.interactor.GetIdInSharedPrefs
import javax.inject.Inject

class StartActivity : BaseActivity(), StartNavigatorListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var startViewModel: StartViewModel? = null

    @Inject
    lateinit var startNavigator: StartNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startViewModel = ViewModelProviders.of(this, viewModelFactory).get(StartViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        getUserPrefIsConnected()
    }

    private fun getUserPrefIsConnected() {
        startViewModel?.getUserPrefIsConnected()?.observe(this, Observer<String> {
            if (it.equals(GetIdInSharedPrefs.ID_USER_DEFAULT)) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

}