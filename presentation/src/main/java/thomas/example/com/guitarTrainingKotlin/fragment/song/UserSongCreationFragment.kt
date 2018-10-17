package thomas.example.com.guitarTrainingKotlin.fragment.song

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_user_song_creation.*
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.data.utils.InstrumentModeUtils
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.viewmodel.song.UserSongCreationViewModel
import javax.inject.Inject

class UserSongCreationFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userSongCreationViewModel: UserSongCreationViewModel

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_song_creation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userSongCreationViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(UserSongCreationViewModel::class.java)

        handleLiveData(view)
        handleClickCreateSong()
    }

    private fun handleLiveData(view: View) {
        userSongCreationViewModel.creationSongSuccess.observeSafe(this) {
            materialDialogComponent.dismissDialog()
            if (it != null && it == true) {
                fragmentManager?.popBackStack()
            } else if (it != null && it == false) {
                if (userSongCreationViewModel.errorThrowable != null) {
                    errorRendererComponent.requestRenderError(
                        userSongCreationViewModel.errorThrowable as Throwable,
                        ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR,
                        view
                    )
                }
                fragmentManager?.popBackStack()
            }
        }

        userSongCreationViewModel.creationSongNotLaunch.observeSafe(this) {
            materialDialogComponent.dismissDialog()
            if (it != null && it == true) {
                if (userSongCreationViewModel.errorThrowable != null) {
                    errorRendererComponent.requestRenderError(
                        Exception(getString(R.string.error_field_not_filled)),
                        ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR,
                        view
                    )
                }
            }
        }
    }

    private fun handleClickCreateSong() {
        fragment_user_song_creation_validation.setOnClickListener {
            materialDialogComponent.showProgressDialog(
                getString(R.string.dialog_creation_song_title),
                getString(R.string.dialog_creation_song_content),
                R.color.colorPrimary
            )

            val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val instrumentMode = InstrumentModeUtils.getIntValueFromInstrumentMode(
                prefs.getString(
                    ModuleSharedPrefsImpl.CURRENT_INSTRUMENT_MODE,
                    ModuleSharedPrefsImpl.INSTRUMENT_MODE_GUITAR
                )
            ).toString()
            userSongCreationViewModel.checkInformationAndValidateCreation(
                fragment_user_song_creation_name.text.toString(),
                fragment_user_song_creation_artist.text.toString(),
                instrumentMode
            )
        }
    }
}