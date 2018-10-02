package thomas.example.com.guitarTrainingKotlin.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_user_panel.*
import kotlinx.android.synthetic.main.view_toolbar.*
import kotlinx.android.synthetic.main.view_toolbar_header.*
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.fragment.program.UserProgramsListFragment
import thomas.example.com.guitarTrainingKotlin.fragment.song.UserSongsListFragment
import thomas.example.com.guitarTrainingKotlin.fragment.user.UserSettingsFragment
import thomas.example.com.guitarTrainingKotlin.viewmodel.program.UserPanelViewModel
import javax.inject.Inject

class UserPanelActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userPanelViewModel: UserPanelViewModel

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    private val navBuilder = NavOptions.Builder()

    private lateinit var idUser: String
    private lateinit var instrumentMode: String
    private lateinit var host: NavHostFragment
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

        host = supportFragmentManager.findFragmentById(R.id.user_panel_nav_host_fragment) as NavHostFragment
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState()
    }

    override fun onStart() {
        super.onStart()
        userPanelViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserPanelViewModel::class.java)

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        this.idUser = prefs.getString(ModuleSharedPrefsImpl.CURRENT_USER_ID, "0")

        if (!idUser.isEmpty()) {
            userPanelViewModel.getUserById(idUser)
        }

        this.instrumentMode = userPanelViewModel.getInstrumentMode(this)

        handleLiveData()
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

    private fun selectDrawerItem(menuItem: MenuItem): Boolean {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        when (menuItem.itemId) {
            R.id.menu_drawer_programs -> displayUserProgramsFragment()
            R.id.menu_drawer_songs -> displayUserSongsFragment()
            R.id.menu_drawer_logout -> logoutUser()
            R.id.menu_drawer_settings -> displayUserSettings()
            else -> displayUserProgramsFragment()
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.isChecked = true
        // Set action bar title
        title = menuItem.title
        // Close the navigation drawer
        activity_main_drawer_layout.closeDrawers()

        return true
    }

    private fun handleLiveData() {
        userPanelViewModel.finishLoading.observe(this, Observer<Boolean> {
            if (it != null) {
                materialDialogComponent.dismissDialog()
                userPanelViewModel.finishLoading.removeObservers(this)
            }
        })

        userPanelViewModel.logoutSucceed.observe(this, Observer<Boolean> {
            if (it != null && it == true) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                userPanelViewModel.logoutSucceed.removeObservers(this)
            }
        })

        userPanelViewModel.getUserSucceed.observe(this, Observer<Boolean> {
            if (it != null && it == true) {
                view_drawer_header_pseudo.text = userPanelViewModel.user.pseudoUser
                view_drawer_header_email.text = userPanelViewModel.user.emailUser
            } else if (it != null && it == false) {
                if (userPanelViewModel.errorThrowable != null) {
                    errorRendererComponent.requestRenderError(userPanelViewModel.errorThrowable as Throwable, ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR, window.decorView.rootView)
                }
                userPanelViewModel.finishLoading.removeObservers(this)
                fragmentManager?.popBackStack()
            }
        })
    }

    private fun displayUserSongsFragment() {
        if ((NavHostFragment.findNavController(host).currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName
                != UserSongsListFragment::class.java.simpleName) {
            val navOptions = navBuilder.setPopUpTo(R.id.user_programs_list, true).build()
            NavHostFragment.findNavController(host).navigate(R.id.user_songs_list, null, navOptions)
        }
    }

    private fun displayUserProgramsFragment() {
        if ((NavHostFragment.findNavController(host).currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName
                != UserProgramsListFragment::class.java.simpleName) {
            val navOptions = navBuilder.setPopUpTo(R.id.user_songs_list, true).build()
            NavHostFragment.findNavController(host).navigate(R.id.user_programs_list, null, navOptions)
        }
    }

    private fun displayUserSettings() {
        if ((NavHostFragment.findNavController(host).currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName
                != UserSettingsFragment::class.java.simpleName) {
            NavHostFragment.findNavController(host).navigate(R.id.action_user_settings, null, null)
        }
    }

    private fun logoutUser() {
        materialDialogComponent.showMultiChoiceDialog(getString(R.string.dialog_logout_title),
                getString(R.string.dialog_logout_confirm_content), R.color.colorPrimary, object : MultipleChoiceMaterialDialogListener {
            override fun onYesSelected() {
                materialDialogComponent.showProgressDialog(getString(R.string.dialog_logout_title), getString(R.string.dialog_logout_content), R.color.colorPrimary)
                userPanelViewModel.logoutUser()
            }
        })
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        // NOTE: Make sure you pass in a valid view_toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return ActionBarDrawerToggle(this, activity_main_drawer_layout, view_toolbar, R.string.user_panel_navigation_drawer_open, R.string.user_panel_navigation_drawer_close)
    }
}