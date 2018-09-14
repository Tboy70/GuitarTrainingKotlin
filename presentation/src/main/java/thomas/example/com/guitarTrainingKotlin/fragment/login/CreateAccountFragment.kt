package thomas.example.com.guitarTrainingKotlin.fragment.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_create_account.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.viewmodel.login.CreateAccountViewModel
import javax.inject.Inject

class CreateAccountFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var createAccountViewModel: CreateAccountViewModel

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAccountViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateAccountViewModel::class.java)

        handleLiveData(view)
        handleClickValidateCreation()
    }

    private fun handleLiveData(view: View) {
        createAccountViewModel.finishLoading.observe(this, Observer<Boolean> {
            if (it != null) {
                materialDialogComponent.dismissDialog()
            }
        })

        createAccountViewModel.creationSuccess.observe(this, Observer<Boolean> {
            if (it != null && it == true) {
                fragmentManager?.popBackStack()
            }
        })

        createAccountViewModel.creationFailure.observe(this, Observer<Boolean> {
            if (it != null && it == true) {
                materialDialogComponent.dismissDialog()
                if (createAccountViewModel.errorThrowable != null) {
                    errorRendererComponent.requestRenderError(createAccountViewModel.errorThrowable as Throwable, ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR, view)
                }
            }
        })
    }

    private fun handleClickValidateCreation() {
        create_account_validate.setOnClickListener {
            materialDialogComponent.showProgressDialog(getString(R.string.dialog_create_account_title), getString(R.string.dialog_create_account_content), R.color.colorPrimary)
            createAccountViewModel.createNewUser(create_account_pseudo.text.toString(), create_account_email.text.toString(), create_account_password.text.toString())
        }
    }

}