package thomas.example.com.guitarTrainingKotlin.fragment.user

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import kotlinx.android.synthetic.main.fragment_user_settings.*
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserSettingsViewModel
import javax.inject.Inject

class UserSettingsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userSettingsViewModel: UserSettingsViewModel

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    private lateinit var currentInstrumentMode: String

    companion object {
        const val CURRENT_USER_INSTRUMENT_MODE = "thomas.example.com.guitarTrainingKotlin.fragment.user.CURRENT_USER_INSTRUMENT_MODE"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userSettingsViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserSettingsViewModel::class.java)

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val currentInstrumentMode = prefs.getString(ModuleSharedPrefsImpl.CURRENT_INSTRUMENT_MODE, ModuleSharedPrefsImpl.INSTRUMENT_MODE_GUITAR)

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
    }

    private fun handleLiveData(view: View) {
        userSettingsViewModel.finishSetInstrumentsModeInSharedPrefs.observe(this, Observer<Boolean> {
            if (it == false) {
                errorRendererComponent.requestRenderError(userSettingsViewModel.errorThrowable as Throwable, ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR, view)
            }
        })
    }

    private fun handleSwitch(switch: Switch, isChecked: Boolean) {
        switch.isChecked = !isChecked
        if (isChecked) {
            userSettingsViewModel.updateInstrumentMode()
        }
    }
}