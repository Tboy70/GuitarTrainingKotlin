package thomas.example.com.guitarTrainingKotlin.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_start.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.listener.StartNavigatorListener
import thomas.example.com.guitarTrainingKotlin.navigator.StartNavigator
import thomas.example.com.guitarTrainingKotlin.viewmodel.StartViewModel
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
        setToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.main_activity_toolbar_about_icon -> true
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun getUserPrefIsConnected() {
        startViewModel?.getUserPrefIsConnected()?.observe(this, Observer<String> {
            Log.e("TEST", "OKTM $it")
        })
    }

    private fun setToolbar() {
        activity_start_toolbar.title = getString(R.string.app_name)
        activity_start_toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        setSupportActionBar(activity_start_toolbar)
    }

}