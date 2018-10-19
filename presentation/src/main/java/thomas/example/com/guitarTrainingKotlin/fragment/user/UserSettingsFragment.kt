package thomas.example.com.guitarTrainingKotlin.fragment.user

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_user_settings.*
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserSettingsViewModel
import javax.inject.Inject

class UserSettingsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userSettingsViewModel: UserSettingsViewModel

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userSettingsViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserSettingsViewModel::class.java)

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val currentInstrumentMode =
            prefs.getString(ModuleSharedPrefsImpl.CURRENT_INSTRUMENT_MODE, ModuleSharedPrefsImpl.INSTRUMENT_MODE_GUITAR)
        val idUser = prefs.getString(ModuleSharedPrefsImpl.CURRENT_USER_ID, "0")

        if (currentInstrumentMode == ModuleSharedPrefsImpl.INSTRUMENT_MODE_GUITAR) {
            settings_guitar_switch.isChecked = true
            settings_bass_switch.isChecked = false
        } else if (currentInstrumentMode == ModuleSharedPrefsImpl.INSTRUMENT_MODE_BASS) {
            settings_guitar_switch.isChecked = false
            settings_bass_switch.isChecked = true
        }

        handleLiveData(view)

        settings_guitar_switch.setOnCheckedChangeListener { _, isChecked ->
            handleSwitch(settings_bass_switch, isChecked)
        }

        settings_bass_switch.setOnCheckedChangeListener { _, isChecked ->
            handleSwitch(settings_guitar_switch, isChecked)
        }

        suppress_account.setOnClickListener {
            materialDialogComponent.showMultiChoiceDialog(getString(R.string.dialog_suppress_account_title),
                    getString(R.string.dialog_suppress_account_confirm_content),
                    R.color.colorPrimary,
                    object : MultipleChoiceMaterialDialogListener {
                        override fun onYesSelected() {
                            if (!idUser.isEmpty()) {
                                userSettingsViewModel.suppressAccount(idUser)
                            }
                        }
                    })
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as UserPanelActivity).setToolbar((activity as UserPanelActivity).getString(R.string.user_panel_navigation_drawer_settings))
    }

    private fun handleLiveData(view: View) {

        userSettingsViewModel.viewState.observeSafe(this) {
            if (it.displayingLoading) {
                materialDialogComponent.showProgressDialog(
                        getString(R.string.dialog_suppress_account_title),
                        getString(R.string.dialog_suppress_account_content),
                        R.color.colorPrimary
                )
            } else {
                materialDialogComponent.dismissDialog()
            }
        }

        userSettingsViewModel.errorEvent.observeSafe(this) {
            errorRendererComponent.requestRenderError(
                    userSettingsViewModel.errorThrowable as Throwable,
                    ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR,
                    view
            )
        }

        userSettingsViewModel.finishSuppressAccount.observeSafe(this) {
            activity?.finish()
        }
    }

    private fun handleSwitch(switch: Switch, isChecked: Boolean) {
        switch.isChecked = !isChecked
        if (isChecked) {
            userSettingsViewModel.updateInstrumentMode()
        }
    }
}