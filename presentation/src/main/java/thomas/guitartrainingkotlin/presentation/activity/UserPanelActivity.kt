package thomas.guitartrainingkotlin.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_user_panel.*
import kotlinx.android.synthetic.main.app_bar_user_panel.*
import kotlinx.android.synthetic.main.nav_header_main.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.data.exception.UserNotFoundException
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.ErrorRendererComponent
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.viewmodel.program.UserPanelViewModel
import javax.inject.Inject

@AndroidEntryPoint
class UserPanelActivity : BaseActivity() {

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var dialogComponent: DialogComponent

    private val viewModel by viewModels<UserPanelViewModel>()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_panel)

        initiateToolbar()
        initiateViewModelObservers()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        viewModel.retrieveUserId(intent.extras?.getString(ConstValues.USER_ID, null))
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initiateToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initiateViewModelObservers() {

        viewModel.userRetrievedLiveEvent.observeSafe(this) {
            view_drawer_header_pseudo.text = it.getUserPseudo()
            view_drawer_header_email.text = it.getUserEmail()
            viewModel.getUserId()?.let { userId ->
                initiateNavGraph(userId)
            }
        }

        viewModel.logoutSucceedLiveEvent.observeSafe(this) {
            if (it != null && it == true) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        viewModel.userNotRetrievedLiveEvent.observeSafe(this) {
            viewModel.logoutUser()
        }

        viewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
            if (it is UserNotFoundException) {
                viewModel.logoutUser()
            }
        }
    }

    private fun initiateNavGraph(userId: String) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        navController.apply {
            setGraph(
                navInflater.inflate(R.navigation.user_panel_nav_graph),
                Bundle().apply {
                    putString(ConstValues.USER_ID, userId)
                }
            )
        }

        // Passing each menu ID as a set of ids because each menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_user_programs_list,
                R.id.nav_user_songs_list,
                R.id.nav_games_list,
                R.id.nav_user_settings,
                R.id.nav_user_legal_notices
            ), drawer_layout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        nav_view.apply {
            setupWithNavController(navController)
            setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_logout -> logoutUser()
                    else -> {
                        navController.navigate(menuItem.itemId, Bundle().apply {
                            putString(ConstValues.USER_ID, viewModel.getUserId())
                        })
                    }
                }
                drawer_layout.closeDrawers()
                true
            }
        }
    }

    private fun logoutUser() {
        dialogComponent.displayDualChoiceDialog(
            R.string.dialog_logout_title,
            R.string.dialog_logout_confirm_content,
            android.R.string.yes,
            android.R.string.no,
            onPositive = { viewModel.logoutUser() },
            onNegative = {}
        )
    }
}