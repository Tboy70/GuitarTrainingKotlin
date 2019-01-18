package thomas.example.com.guitarTrainingKotlin.fragment.login

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_create_account.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.viewmodel.login.CreateAccountViewModel
import javax.inject.Inject

class CreateAccountFragment : BaseFragment<CreateAccountViewModel>() {

    override val viewModelClass = CreateAccountViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_create_account

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleLiveData(view)
        handleClickValidateCreation()
    }

    private fun handleLiveData(view: View) {
        viewModel.creationSuccess.observeSafe(this) {
            materialDialogComponent.dismissDialog()
            fragmentManager?.popBackStack()
            val snackbar = Snackbar.make(
                activity!!.findViewById(android.R.id.content),
                getString(R.string.success_create_account),
                Snackbar.LENGTH_LONG
            )
            snackbar.view.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorSuccess))
            snackbar.show()
        }

        viewModel.viewState.observeSafe(this) {
            if (it.displayingLoading) {
                materialDialogComponent.showProgressDialog(
                    getString(R.string.dialog_create_account_title),
                    getString(R.string.dialog_create_account_content),
                    R.color.colorPrimary
                )
            } else {
                materialDialogComponent.dismissDialog()
            }
        }

        viewModel.errorEvent.observeSafe(this) {
            materialDialogComponent.dismissDialog()
            val errorTriggered = viewModel.errorThrowable
            if (it.ERROR_TRIGGERED && errorTriggered != null) {
                errorRendererComponent.requestRenderError(
                    viewModel.errorThrowable as Throwable,
                    ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR,
                    view
                )
            }
        }
    }

    private fun handleClickValidateCreation() {
        create_account_validate.setOnClickListener {
            viewModel.createNewUser(
                create_account_pseudo.text.toString(),
                create_account_email.text.toString(),
                create_account_password.text.toString()
            )
        }
    }
}