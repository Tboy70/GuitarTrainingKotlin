package thomas.example.com.guitarTrainingKotlin.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_user_panel.*
import kotlinx.android.synthetic.main.view_toolbar.*
import kotlinx.android.synthetic.main.view_toolbar_header.*
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.other.LegalNoticesFragment
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

    private lateinit var idUser: String
    private lateinit var instrumentMode: String
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

    override fun onStart() {
        super.onStart()
        userPanelViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserPanelViewModel::class.java)

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        this.idUser = prefs.getString(ModuleSharedPrefsImpl.CURRENT_USER_ID, "0")

        if (!idUser.isEmpty() && idUser != "0") {
            userPanelViewModel.getUserById(idUser)
        } else {
            backToLogin()
        }

        this.instrumentMode = userPanelViewModel.getInstrumentMode(this)

        handleLiveData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp() =
        findNavController(this, R.id.user_panel_nav_host_fragment).navigateUp()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig)
    }

    fun setToolbar(toolbarName: String) {
        view_toolbar.title = toolbarName
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
        navigationView.setCheckedItem(R.id.menu_drawer_programs)
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        // NOTE: Make sure you pass in a valid view_toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return ActionBarDrawerToggle(
                this,
                activity_main_drawer_layout,
                view_toolbar,
                R.string.user_panel_navigation_drawer_open,
                R.string.user_panel_navigation_drawer_close
        )
    }

    private fun selectDrawerItem(menuItem: MenuItem): Boolean {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        when (menuItem.itemId) {
            R.id.menu_drawer_programs -> displayUserProgramsFragment()
            R.id.menu_drawer_songs -> displayUserSongsFragment()
            R.id.menu_drawer_logout -> logoutUser()
            R.id.menu_drawer_settings -> displayUserSettings()
            R.id.menu_drawer_legal_notices -> displayLegalNotices()
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

        userPanelViewModel.logoutSucceed.observeSafe(this) {
            if (it != null && it == true) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                userPanelViewModel.logoutSucceed.removeObservers(this)
            }
        }

        userPanelViewModel.viewState.observeSafe(this) {
            if (it.displayingLoading) {
                materialDialogComponent.showProgressDialog(
                        getString(R.string.dialog_logout_title),
                        getString(R.string.dialog_logout_content),
                        R.color.colorPrimary
                )
            } else {
                materialDialogComponent.dismissDialog()
            }
        }

        userPanelViewModel.errorEvent.observeSafe(this) {
            val errorTriggered = userPanelViewModel.errorThrowable
            if (it.ERROR_TRIGGERED && errorTriggered != null) {
                errorRendererComponent.requestRenderError(
                        errorTriggered,
                        ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR,
                        window.decorView.rootView
                )
            }
        }

        userPanelViewModel.userRetrieved.observeSafe(this) {
            view_drawer_header_pseudo.text = it.getPseudoUser()
            view_drawer_header_email.text = it.getEmailUser()
        }
    }

    private fun displayUserSongsFragment() {
        when ((findNavController(R.id.user_panel_nav_host_fragment).currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName) {
            UserProgramsListFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
                    R.id.action_user_programs_list_to_user_songs_list
            )
            UserSettingsFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
                    R.id.action_action_user_settings_to_user_songs_list
            )
            LegalNoticesFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
                    R.id.action_action_legal_notices_to_user_songs_list
            )
        }
    }

    private fun displayUserProgramsFragment() {
        when ((findNavController(R.id.user_panel_nav_host_fragment).currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName) {
            UserSongsListFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
                    R.id.action_user_songs_list_to_user_programs_list
            )
            UserSettingsFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
                    R.id.action_action_user_settings_to_user_programs_list
            )
            LegalNoticesFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
                    R.id.action_action_legal_notices_to_user_programs_list
            )
        }
    }

    private fun displayUserSettings() {
        when ((findNavController(R.id.user_panel_nav_host_fragment).currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName) {
            UserProgramsListFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
                    R.id.action_user_programs_list_to_action_user_settings
            )
            UserSongsListFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
                    R.id.action_user_songs_list_to_action_user_settings
            )
            LegalNoticesFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
                    R.id.action_action_legal_notices_to_action_user_settings
            )
        }
    }

    private fun displayLegalNotices() {
        when ((findNavController(R.id.user_panel_nav_host_fragment).currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName) {
            UserProgramsListFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
                    R.id.action_user_programs_list_to_action_legal_notices
            )
            UserSongsListFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
                    R.id.action_user_songs_list_to_action_legal_notices
            )
            UserSettingsFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
                    R.id.action_action_user_settings_to_action_legal_notices
            )
        }
    }

    private fun logoutUser() {
        materialDialogComponent.showMultiChoiceDialog(getString(R.string.dialog_logout_title),
                getString(R.string.dialog_logout_confirm_content),
                R.color.colorPrimary,
                object : MultipleChoiceMaterialDialogListener {
                    override fun onYesSelected() {
                        userPanelViewModel.logoutUser()
                    }
                })
    }

    private fun backToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}