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
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.component.listener.SnackbarComponent
import thomas.guitartrainingkotlin.presentation.extension.getInput
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.extension.textChangedListener
import thomas.guitartrainingkotlin.presentation.viewmodel.login.ForgotPasswordViewModel
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    @Inject
    lateinit var snackbarComponent: SnackbarComponent

    private val viewModel by viewModels<ForgotPasswordViewModel>()

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
        (activity as AppCompatActivity).setSupportActionBar(fragment_forgot_password_toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initiateViews() {
        fragment_forgot_password_email.addTextChangedListener(textChangedListener)
        fragment_forgot_password_validate.setOnClickListener {
            viewModel.retrievePassword(fragment_forgot_password_email.getInput())
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.emailSentLiveEvent.observeSafe(this) {
            findNavController().navigateUp()

            activity?.let { activity ->
                snackbarComponent.displaySnackbar(
                    activity.findViewById(android.R.id.content),
                    getString(R.string.success_forgot_password_email_sent),
                    Snackbar.LENGTH_LONG,
                    true
                )
            }
        }
    }

    private fun updateConfirmButtonState() {
        fragment_forgot_password_validate.isEnabled =
            Patterns.EMAIL_ADDRESS.matcher(fragment_forgot_password_email.getInput()).matches()
    }
}