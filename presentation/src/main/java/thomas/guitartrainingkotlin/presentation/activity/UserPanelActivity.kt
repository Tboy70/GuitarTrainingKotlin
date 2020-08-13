package thomas.guitartrainingkotlin.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_user_panel.*
import kotlinx.android.synthetic.main.view_toolbar.*
import kotlinx.android.synthetic.main.view_toolbar_header.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.data.manager.sharedprefs.SharedPrefsManagerImpl
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.ErrorRendererComponent
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.fragment.other.LegalNoticesFragment
import thomas.guitartrainingkotlin.presentation.fragment.program.UserProgramsListFragment
import thomas.guitartrainingkotlin.presentation.fragment.song.UserSongsListFragment
import thomas.guitartrainingkotlin.presentation.fragment.user.UserSettingsFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.viewmodel.program.UserPanelViewModel
import javax.inject.Inject

@AndroidEntryPoint
class UserPanelActivity : BaseActivity() {

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent
    @Inject
    lateinit var dialogComponent: DialogComponent

    private var drawerToggle: ActionBarDrawerToggle? = null

    private val viewModel by viewModels<UserPanelViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_panel)

        initiateToolbar()
        initiateDrawerMenu()
        initiateViewModelObservers()

        viewModel.retrieveUserId()
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

    override fun onSupportNavigateUp() =
        findNavController(this, R.id.user_panel_nav_host_fragment).navigateUp()

    fun setToolbar(toolbarName: String?) {
        toolbarName?.let {
            view_toolbar.title = it
        }
    }

    private fun initiateToolbar() {
        setSupportActionBar(view_toolbar)
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
            R.id.menu_drawer_programs -> getActionForUserProgramsDestination()
            R.id.menu_drawer_songs -> getActionForUserSongsDestination()
            R.id.menu_drawer_games -> launchGameActivity()
            R.id.menu_drawer_settings -> getActionForSettingsDestination()
            R.id.menu_drawer_legal_notices -> getActionForLegalNoticesDestination()
            R.id.menu_drawer_logout -> logoutUser()
            else -> getActionForUserProgramsDestination()
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

        viewModel.userNotRetrievedLiveEvent.observeSafe(this) {
            backToLogin()
        }

        viewModel.logoutSucceedLiveEvent.observeSafe(this) {
            if (it != null && it == true) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        viewModel.userRetrievedLiveEvent.observeSafe(this) {
            view_drawer_header_pseudo.text = it.getUserPseudo()
            view_drawer_header_email.text = it.getUserEmail()
            viewModel.getUserId()?.let { userId ->
                initiateNavGraph(userId)
            }
        }

        viewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }
    }

    private fun initiateNavGraph(userId: String) {
        val navHostFragment = user_panel_nav_host_fragment as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.user_panel_nav_graph)
        val bundle = Bundle()
        bundle.putString(ConstValues.USER_ID, userId)
        navHostFragment.navController.setGraph(graph, bundle)
    }

    private fun logoutUser() {
        dialogComponent.displayDualChoiceDialog(
            R.string.dialog_logout_title,
            R.string.dialog_logout_confirm_content,
            android.R.string.yes,
            android.R.string.no,
            onPositive = {
                viewModel.logoutUser()
            },
            onNegative = {}
        )
    }

    private fun backToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    /*************** NAVIGATION PART ***************/

    private fun getActionForUserProgramsDestination() {
        when ((findNavController(R.id.user_panel_nav_host_fragment).currentDestination as FragmentNavigator.Destination).className) {
            UserSongsListFragment::class.java.name -> R.id.action_user_songs_list_to_user_programs_list
            UserSettingsFragment::class.java.name -> R.id.action_action_user_settings_to_user_programs_list
            LegalNoticesFragment::class.java.name -> R.id.action_action_legal_notices_to_user_programs_list
            else -> null
        }?.let { chosenDestination ->
            navigate(chosenDestination)
        }
    }

    private fun getActionForUserSongsDestination() {
        when ((findNavController(R.id.user_panel_nav_host_fragment).currentDestination as FragmentNavigator.Destination).className) {
            UserProgramsListFragment::class.java.name -> R.id.action_user_programs_list_to_user_songs_list
            UserSettingsFragment::class.java.name -> R.id.action_action_user_settings_to_user_songs_list
            LegalNoticesFragment::class.java.name -> R.id.action_action_legal_notices_to_user_songs_list
            else -> null
        }?.let { chosenDestination ->
            navigate(chosenDestination)
        }
    }

    private fun getActionForSettingsDestination() {
        when ((findNavController(R.id.user_panel_nav_host_fragment).currentDestination as FragmentNavigator.Destination).className) {
            UserProgramsListFragment::class.java.name -> R.id.action_user_programs_list_to_action_user_settings
            UserSongsListFragment::class.java.name -> R.id.action_user_songs_list_to_action_user_settings
            LegalNoticesFragment::class.java.name -> R.id.action_action_legal_notices_to_action_user_settings
            else -> null
        }?.let { chosenDestination ->
            navigate(chosenDestination)
        }
    }

    private fun getActionForLegalNoticesDestination() {
        when ((findNavController(R.id.user_panel_nav_host_fragment).currentDestination as FragmentNavigator.Destination).className) {
            UserProgramsListFragment::class.java.name -> R.id.action_user_programs_list_to_action_legal_notices
            UserSongsListFragment::class.java.name -> R.id.action_user_songs_list_to_action_legal_notices
            UserSettingsFragment::class.java.name -> R.id.action_action_user_settings_to_action_legal_notices
            else -> null
        }?.let { chosenDestination ->
            navigate(chosenDestination)
        }
    }

    private fun navigate(destinationId: Int) {
        val bundle = Bundle()
        bundle.putString(ConstValues.USER_ID, viewModel.getUserId())
        findNavController(R.id.user_panel_nav_host_fragment).navigate(destinationId, bundle)
    }

    private fun launchGameActivity() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}