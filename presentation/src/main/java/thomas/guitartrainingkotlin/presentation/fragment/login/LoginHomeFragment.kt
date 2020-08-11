package thomas.guitartrainingkotlin.presentation.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login_home.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.GameActivity
import thomas.guitartrainingkotlin.presentation.activity.UserPanelActivity
import thomas.guitartrainingkotlin.presentation.component.listener.ErrorRendererComponent
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.utils.KeyboardUtils
import thomas.guitartrainingkotlin.presentation.viewmodel.login.LoginHomeViewModel
import javax.inject.Inject

@AndroidEntryPoint
class LoginHomeFragment : Fragment(R.layout.fragment_login_home) {

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    private val viewModel by viewModels<LoginHomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateToolbar()
        initiateViews()
        initiateViewModelObservers()

        viewModel.retrieveSavedValues()
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(false)
        (activity as AppCompatActivity).setSupportActionBar(fragment_login_toolbar)
    }

    private fun initiateViews() {

        fragment_login_home_username.addTextChangedListener(textChangedListener {
            viewModel.saveUserPseudoValue(fragment_login_home_username.getInput())
        })

        fragment_login_home_password.addTextChangedListener(textChangedListener {
            viewModel.saveUserPasswordValue(fragment_login_home_password.getInput())
        })

        // Create account button
        fragment_login_home_create_account.setOnClickListener {
            findNavController().navigate(
                R.id.fragment_create_account,
                null,
                null
            )
        }

        fragment_login_home_forgot_password.setOnClickListener {
            findNavController().navigate(
                R.id.fragment_forgot_password,
                null,
                null
            )
        }

        fragment_login_home_game_access.setOnClickListener {
            val intent = Intent(activity, GameActivity::class.java)
            startActivity(intent)
        }

        // Validate button
        fragment_login_home_validate_button.setOnClickListener {
            activity?.let { fragmentActivity ->
                KeyboardUtils.hideKeyboard(fragmentActivity)
            }

            viewModel.connectUser(
                fragment_login_home_username.getInput(),
                fragment_login_home_password.getInput()
            )
        }
    }

    private fun initiateViewModelObservers() {

        viewModel.retrievedUserLiveData.observeSafe(this) {
            if (it) {
                connectSuccess()
            }
        }

        viewModel.savedUserPseudoLiveData.observeSafe(this) { retrievedPseudo ->
            fragment_login_home_username.setText(retrievedPseudo)
        }

        viewModel.savedUserPasswordLiveData.observeSafe(this) { retrievedPassword ->
            fragment_login_home_password.setText(retrievedPassword)
        }

        viewModel.viewState.observeSafe(this) {
            if (it.loading) {
                fragment_login_home_progress_bar.show()
            } else {
                fragment_login_home_progress_bar.gone()
            }
        }

        viewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }
    }

    private fun connectSuccess() {
        val intent = Intent(activity, UserPanelActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}
