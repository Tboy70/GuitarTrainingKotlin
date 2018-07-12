package thomas.example.com.guitarTrainingKotlin.fragment

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login_home.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.utils.KeyboardUtils
import thomas.example.com.guitarTrainingKotlin.viewmodel.LoginHomeViewModel
import javax.inject.Inject

class LoginHomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var loginHomeViewModel: LoginHomeViewModel? = null

    companion object {

        //Todo : Is new instance really useful as we have the navigation view now ?
        fun newInstance(): LoginHomeFragment {
            return LoginHomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginHomeViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginHomeViewModel::class.java)

        fragment_login_home_validate_button.setOnClickListener { handleClickValidateLogin() }
    }

    private fun handleClickValidateLogin() {
        KeyboardUtils.hideKeyboard(this.activity!!)
        loginHomeViewModel?.connectUser(fragment_login_home_username.text.toString(), fragment_login_home_password.text.toString())
    }

}