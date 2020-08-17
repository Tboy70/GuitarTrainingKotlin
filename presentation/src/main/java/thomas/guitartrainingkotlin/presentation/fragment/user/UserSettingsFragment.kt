package thomas.guitartrainingkotlin.presentation.fragment.user

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_settings.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues
import thomas.guitartrainingkotlin.presentation.component.DialogComponentImpl
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.extension.hide
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.extension.setCustomChecked
import thomas.guitartrainingkotlin.presentation.extension.show
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.viewmodel.user.UserSettingsViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserSettingsFragment : Fragment(R.layout.fragment_user_settings) {

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var dialogComponent: DialogComponentImpl

    private val viewModel by viewModels<UserSettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            viewModel.setUserId(it.getString(ConstValues.USER_ID))
        }

//        initiateToolbar()
        initiateView()
        initiateViewModelObservers()
    }

    private fun initiateView() {
        user_settings_guitar_switch.setOnCheckedChangeListener { _, isChecked ->
            handleSwitch(isChecked)
        }

        user_settings_bass_switch.setOnCheckedChangeListener { _, isChecked ->
            handleSwitch(isChecked)
        }


        user_settings_suppress_account.setOnClickListener {
            dialogComponent.displayDualChoiceDialog(
                R.string.dialog_suppress_account_title,
                R.string.dialog_suppress_account_confirm_content,
                android.R.string.yes,
                android.R.string.no,
                onPositive = {
                    viewModel.suppressAccount()
                },
                onNegative = {}
            )
        }
    }

    private fun initiateViewModelObservers() {

        viewModel.retrievedInstrumentModeLiveData.observeSafe(this) {
            if (it == InstrumentModeValues.INSTRUMENT_MODE_GUITAR) {
                user_settings_guitar_switch.setCustomChecked(
                    true,
                    CompoundButton.OnCheckedChangeListener { _, isChecked: Boolean ->
                        handleSwitch(isChecked)
                    })
                user_settings_bass_switch.setCustomChecked(
                    false,
                    CompoundButton.OnCheckedChangeListener { _, isChecked: Boolean ->
                        handleSwitch(isChecked)
                    })
            } else if (it == InstrumentModeValues.INSTRUMENT_MODE_BASS) {
                user_settings_guitar_switch.setCustomChecked(
                    false,
                    CompoundButton.OnCheckedChangeListener { _, isChecked: Boolean ->
                        handleSwitch(isChecked)
                    })
                user_settings_bass_switch.setCustomChecked(
                    true,
                    CompoundButton.OnCheckedChangeListener { _, isChecked: Boolean ->
                        handleSwitch(isChecked)
                    })
            }
        }

        viewModel.viewState.observeSafe(this) {
            if (it.loading) {
                user_settings_progress_bar.show()
            } else {
                user_settings_progress_bar.hide()
            }
        }

        viewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }

        viewModel.suppressedAccountLiveEvent.observeSafe(this) {
            activity?.finish()
        }
    }

    private fun handleSwitch(isChecked: Boolean) {
        if (isChecked) {
            viewModel.updateInstrumentMode()
        }
    }
}