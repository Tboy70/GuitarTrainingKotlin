package thomas.example.com.guitarTrainingKotlin.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_login.*
import thomas.example.com.guitarTrainingKotlin.R

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()
        setToolbar()
        launchConnectionScreen()
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

    private fun setToolbar() {
        activity_login_toolbar.title = getString(R.string.app_name)
        activity_login_toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        setSupportActionBar(activity_login_toolbar)
    }

    private fun launchConnectionScreen() {
        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.login_nav_host_fragment) as NavHostFragment?
                ?: return
        NavHostFragment.findNavController(host).navigate(R.id.launcher_login)
    }
}