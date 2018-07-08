package thomas.example.com.guitarTrainingKotlin.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.listener.StartNavigatorListener
import thomas.example.com.guitarTrainingKotlin.navigator.StartNavigator
import thomas.example.com.guitarTrainingKotlin.presenter.StartPresenter
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : BaseActivity(), StartNavigatorListener {

    @Inject
    lateinit var startPresenter: StartPresenter

    @Inject
    lateinit var startNavigator: StartNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    override fun onStart() {
        super.onStart()
//        getUserPrefIsConnected()
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
        startPresenter.getUserPrefIsConnected()
    }

    private fun setToolbar() {
        activity_start_toolbar.title = getString(R.string.app_name)
        activity_start_toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        setSupportActionBar(activity_start_toolbar)
    }

}