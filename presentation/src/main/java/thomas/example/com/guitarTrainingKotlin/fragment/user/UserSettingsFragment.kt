package thomas.example.com.guitarTrainingKotlin.fragment.user

import android.os.Bundle
import android.view.View
import android.widget.Switch
import kotlinx.android.synthetic.main.fragment_user_settings.*
import thomas.example.com.data.manager.SharedPrefsManagerImpl
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.component.DialogComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.extension.hide
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.extension.show
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserSettingsViewModel
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
            handleSwitch(user_settings_bass_switch, isChecked)
        }

        user_settings_bass_switch.setOnCheckedChangeListener { _, isChecked ->
            handleSwitch(user_settings_guitar_switch, isChecked)
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
                user_settings_guitar_switch.isChecked = true
                user_settings_bass_switch.isChecked = false
            } else if (it == SharedPrefsManagerImpl.INSTRUMENT_MODE_BASS) {
                user_settings_guitar_switch.isChecked = false
                user_settings_bass_switch.isChecked = true
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

    private fun handleSwitch(switch: Switch, isChecked: Boolean) {
        switch.isChecked = !isChecked
        if (isChecked) {
            viewModel.updateInstrumentMode()
        }
    }
}