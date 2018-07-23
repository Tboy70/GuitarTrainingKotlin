package thomas.example.com.guitarTrainingKotlin.activity

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_user_panel.*
import kotlinx.android.synthetic.main.view_toolbar.*
import thomas.example.com.guitarTrainingKotlin.R

class UserPanelActivity : BaseActivity() {

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_panel)
        initDrawerMenu()

        drawerToggle.setToolbarNavigationClickListener {
            if (!drawerToggle.isDrawerIndicatorEnabled) {
                onBackPressed()
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig)
    }

    private fun initDrawerMenu() {
        // Setup drawer view
        setupDrawerContent(activity_user_panel_navigation_view)

        // Set a Toolbar to replace the ActionBar.
        setSupportActionBar(view_toolbar)

        // Find our drawer view
        drawerToggle = setupDrawerToggle()

        // Tie DrawerLayout events to the ActionBarToggle
        activity_main_drawer_layout.addDrawerListener(drawerToggle)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener {
            selectDrawerItem(it)
        }
    }

    private fun selectDrawerItem(it: MenuItem): Boolean {
        return true
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        // NOTE: Make sure you pass in a valid view_toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return ActionBarDrawerToggle(this, activity_main_drawer_layout, view_toolbar, R.string.user_panel_navigation_drawer_open, R.string.user_panel_navigation_drawer_close)
    }
}