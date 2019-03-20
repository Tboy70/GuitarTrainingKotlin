package thomas.guitartrainingkotlin.presentation.fragment.user

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.fragment_user_settings.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.data.manager.sharedprefs.SharedPrefsManagerImpl
import thomas.guitartrainingkotlin.presentation.activity.UserPanelActivity
import thomas.guitartrainingkotlin.presentation.component.DialogComponentImpl
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.extension.hide
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.extension.setCustomChecked
import thomas.guitartrainingkotlin.presentation.extension.show
import thomas.guitartrainingkotlin.presentation.fragment.BaseFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.viewmodel.user.UserSettingsViewModel
import javax.inject.Inject

class UserSettingsFragment : BaseFragment<UserSettingsViewModel>() {

    override val viewModelClass = UserSettingsViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_settings

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var dialogComponent: DialogComponentImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            viewModel.setUserId(it.getString(ConstValues.USER_ID))
        }

        initiateToolbar()
        initiateView()
        initiateViewModelObservers()
    }

    private fun initiateToolbar() {
        (activity as UserPanelActivity).setToolbar((activity as UserPanelActivity).getString(R.string.user_panel_navigation_drawer_settings))
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
            if (it == SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR) {
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
            } else if (it == SharedPrefsManagerImpl.INSTRUMENT_MODE_BASS) {
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