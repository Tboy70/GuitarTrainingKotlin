package thomas.example.com.guitarTrainingKotlin.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_user_panel.*
import kotlinx.android.synthetic.main.view_toolbar.*
import kotlinx.android.synthetic.main.view_toolbar_header.*
import thomas.example.com.data.manager.SharedPrefsManagerImpl
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.listener.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.DialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.viewmodel.factory.ViewModelFactory
import thomas.example.com.guitarTrainingKotlin.viewmodel.program.UserPanelViewModel
import javax.inject.Inject

class UserPanelActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var userPanelViewModel: UserPanelViewModel
    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent
    @Inject
    lateinit var dialogComponent: DialogComponent

    private var userId: String? = null
    // TODO : Refactor --> Why string and int !?
    private var instrumentMode: String = SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR
    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_panel)

        userPanelViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserPanelViewModel::class.java)

        initiateDrawerMenu()
        initiateViewModelObservers()

        userId = PreferenceManager.getDefaultSharedPreferences(this)
            .getString(SharedPrefsManagerImpl.CURRENT_USER_ID, "0")

        userId?.let { userId ->
            if (!userId.isEmpty() && userId != "0") {
                userPanelViewModel.getUserById(userId)
            } else {
                backToLogin()
            }
        }

        this.instrumentMode = userPanelViewModel.getInstrumentMode(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle?.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        drawerToggle?.let {
            return it.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
        } ?: return false
    }

    // TODO : Useful ?
    override fun onSupportNavigateUp() =
        findNavController(this, R.id.user_panel_nav_host_fragment).navigateUp()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Pass any configuration change to the drawer toggles
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    fun setToolbar(toolbarName: String) {
        view_toolbar.title = toolbarName
    }

    private fun initiateDrawerMenu() {
        // Setup drawer view
        setupDrawerContent(activity_user_panel_navigation_view)

        // Set a Toolbar to replace the ActionBar.
        setSupportActionBar(view_toolbar)

        // Find our drawer view
        drawerToggle = setupDrawerToggle()

        drawerToggle?.run {
            // Tie DrawerLayout events to the ActionBarToggle
            activity_main_drawer_layout.addDrawerListener(this)
            this.setToolbarNavigationClickListener {
                if (!this.isDrawerIndicatorEnabled) {
                    onBackPressed()
                }
            }
        }
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

    private fun initiateViewModelObservers() {

        userPanelViewModel.logoutSucceed.observeSafe(this) {
            if (it != null && it == true) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                userPanelViewModel.logoutSucceed.removeObservers(this)
            }
        }

        userPanelViewModel.userRetrievedLiveEvent.observeSafe(this) {
            view_drawer_header_pseudo.text = it.getUserPseudo()
            view_drawer_header_email.text = it.getUserEmail()
        }

        userPanelViewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }
    }

    private fun displayUserSongsFragment() {
//        when ((findNavController(R.id.user_panel_nav_host_fragment).currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName) {
//            UserProgramsListFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
//                    R.id.action_user_programs_list_to_user_songs_list
//            )
//            UserSettingsFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
//                    R.id.action_action_user_settings_to_user_songs_list
//            )
//            LegalNoticesFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
//                    R.id.action_action_legal_notices_to_user_songs_list
//            )
//        }
    }

    private fun displayUserProgramsFragment() {
//        when ((findNavController(R.id.user_panel_nav_host_fragment).currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName) {
//            UserSongsListFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
//                    R.id.action_user_songs_list_to_user_programs_list
//            )
//            UserSettingsFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
//                    R.id.action_action_user_settings_to_user_programs_list
//            )
//            LegalNoticesFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
//                    R.id.action_action_legal_notices_to_user_programs_list
//            )
//        }
    }

    private fun displayUserSettings() {
//        when ((findNavController(R.id.user_panel_nav_host_fragment).currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName) {
//            UserProgramsListFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
//                    R.id.action_user_programs_list_to_action_user_settings
//            )
//            UserSongsListFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
//                    R.id.action_user_songs_list_to_action_user_settings
//            )
//            LegalNoticesFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
//                    R.id.action_action_legal_notices_to_action_user_settings
//            )
//        }
    }

    private fun displayLegalNotices() {
//        when ((findNavController(R.id.user_panel_nav_host_fragment).currentDestination as FragmentNavigator.Destination).fragmentClass.simpleName) {
//            UserProgramsListFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
//                    R.id.action_user_programs_list_to_action_legal_notices
//            )
//            UserSongsListFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
//                    R.id.action_user_songs_list_to_action_legal_notices
//            )
//            UserSettingsFragment::class.java.simpleName -> findNavController(R.id.user_panel_nav_host_fragment).navigate(
//                    R.id.action_action_user_settings_to_action_legal_notices
//            )
//        }
    }

    private fun logoutUser() {
//        dialogComponent.showMultiChoiceDialog(getString(R.string.dialog_logout_title),
//            getString(R.string.dialog_logout_confirm_content),
//            R.color.colorPrimary,
//            object : MultipleChoiceMaterialDialogListener {
//                override fun onYesSelected() {
//                    userPanelViewModel.logoutUser()
//                }
//            })
    }

    private fun backToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}