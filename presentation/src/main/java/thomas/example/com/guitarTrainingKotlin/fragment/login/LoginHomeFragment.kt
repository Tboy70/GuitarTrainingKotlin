package thomas.example.com.guitarTrainingKotlin.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login_home.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.utils.KeyboardUtils
import thomas.example.com.guitarTrainingKotlin.viewmodel.login.LoginHomeViewModel
import javax.inject.Inject

class LoginHomeFragment : BaseFragment<LoginHomeViewModel>() {

    override val viewModelClass = LoginHomeViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_login_home

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateToolbar()
        initiateViews()
        initiateViewModelObservers()
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(false)
        (activity as AppCompatActivity).setSupportActionBar(fragment_login_toolbar)
    }

    private fun initiateViews() {

        // Create account button
        fragment_login_home_create_account.setOnClickListener {
            findNavController().navigate(
                R.id.fragment_create_account,
                null,
                null
            )
        }

        // Validate button
        fragment_login_home_validate_button.setOnClickListener {
            activity?.let { fragmentActivity ->
                KeyboardUtils.hideKeyboard(fragmentActivity)
            }

            viewModel.connectUser(
                fragment_login_home_username.text.toString(),
                fragment_login_home_password.text.toString()
            )
        }
    }

    private fun initiateViewModelObservers() {

        viewModel.retrievedUser.observeSafe(this) {
            if (it) {
                connectSuccess()
            }
        }

        viewModel.viewState.observeSafe(this) {
            if (it.loading) {
                materialDialogComponent.showProgressDialog(
                    getString(R.string.dialog_login_title),
                    getString(R.string.dialog_login_content),
                    R.color.colorPrimary
                )
            } else {
                materialDialogComponent.dismissDialog()
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
