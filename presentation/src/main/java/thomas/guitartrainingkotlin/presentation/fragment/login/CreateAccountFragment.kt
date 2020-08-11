package thomas.guitartrainingkotlin.presentation.fragment.login

import android.os.Bundle
import android.text.TextWatcher
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_account.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.component.listener.ErrorRendererComponent
import thomas.guitartrainingkotlin.presentation.component.listener.SnackbarComponent
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.viewmodel.login.CreateAccountViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CreateAccountFragment : Fragment(R.layout.fragment_create_account) {

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var snackbarComponent: SnackbarComponent

    private val viewModel by viewModels<CreateAccountViewModel>()

    private val textChangedListener: TextWatcher = textChangedListener {
        updateConfirmButtonState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateToolbar()
        initiateViews()
        initiateViewModelObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(fragment_create_account_toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initiateViews() {
        fragment_create_account_pseudo.addTextChangedListener(textChangedListener)
        fragment_create_account_email.addTextChangedListener(textChangedListener)
        fragment_create_account_password.addTextChangedListener(textChangedListener)
        // Account creation validation
        fragment_create_account_validate.setOnClickListener {
            viewModel.createNewUser(
                fragment_create_account_pseudo.getInput(),
                fragment_create_account_email.getInput(),
                fragment_create_account_password.getInput()
            )
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.userCreationLiveData.observeSafe(this) {
            findNavController().navigateUp()

            activity?.let { activity ->
                snackbarComponent.displaySnackbar(
                    activity.findViewById(android.R.id.content),
                    getString(R.string.success_create_account),
                    Snackbar.LENGTH_LONG,
                    true
                )
            }
        }

        viewModel.viewState.observeSafe(this) {
            if (it.loading) {
                fragment_create_account_progress_bar.show()
            } else {
                fragment_create_account_progress_bar.gone()
            }
        }

        viewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }
    }

    private fun updateConfirmButtonState() {
        fragment_create_account_validate.isEnabled =
            fragment_create_account_pseudo.getInput().isNotEmpty() &&
                    fragment_create_account_email.getInput().isNotEmpty() &&
                    Patterns.EMAIL_ADDRESS.matcher(fragment_create_account_email.getInput())
                        .matches() &&
                    fragment_create_account_password.getInput().isNotEmpty()
    }
}