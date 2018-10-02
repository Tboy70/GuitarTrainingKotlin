package thomas.example.com.guitarTrainingKotlin.fragment.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_login_home.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.LoginActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.utils.KeyboardUtils
import thomas.example.com.guitarTrainingKotlin.viewmodel.login.LoginHomeViewModel
import javax.inject.Inject

class LoginHomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var loginHomeViewModel: LoginHomeViewModel

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginHomeViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginHomeViewModel::class.java)

        handleLiveData(view)
        handleClickValidateLogin()
        handleClickCreateAccount()
    }

    private fun handleLiveData(view: View) {
        loginHomeViewModel.finishLoading.observe(this, Observer<Boolean> {
            if (it != null) {
                materialDialogComponent.dismissDialog()
            }
        })

        loginHomeViewModel.connectSucceed.observe(this, Observer<Boolean> {
            if (it != null && it == true) {
                connectSuccess()
            } else if (it != null && it == false) {
                materialDialogComponent.dismissDialog()
                if (loginHomeViewModel.errorThrowable != null) {
                    errorRendererComponent.requestRenderError(loginHomeViewModel.errorThrowable as Throwable, ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR, view)
                }
            }
        })
    }

    private fun handleClickValidateLogin() {
        fragment_login_home_validate_button.setOnClickListener {
            KeyboardUtils.hideKeyboard(this.activity as LoginActivity)
            materialDialogComponent.showProgressDialog(getString(R.string.dialog_login_title), getString(R.string.dialog_login_content), R.color.colorPrimary)
            loginHomeViewModel.connectUser(fragment_login_home_username.text.toString(), fragment_login_home_password.text.toString())
        }
    }

    private fun handleClickCreateAccount() {
        fragment_login_home_create_account.setOnClickListener {
            val host = activity?.supportFragmentManager?.findFragmentById(R.id.login_nav_host_fragment) as NavHostFragment
            NavHostFragment.findNavController(host).navigate(R.id.fragment_create_account, null, null)
        }
    }

    private fun connectSuccess() {
        val intent = Intent(activity, UserPanelActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}