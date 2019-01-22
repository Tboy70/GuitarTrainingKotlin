package thomas.example.com.guitarTrainingKotlin.fragment.song

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_user_song_creation.*
import thomas.example.com.data.manager.SharedPrefsManagerImpl
import thomas.example.com.data.utils.InstrumentModeUtils
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponentImpl
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.viewmodel.song.UserSongCreationViewModel
import javax.inject.Inject

class UserSongCreationFragment : BaseFragment<UserSongCreationViewModel>() {

    override val viewModelClass = UserSongCreationViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_song_creation

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var materialDialogComponentImpl: MaterialDialogComponentImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleLiveData(view)
        handleClickCreateSong()
    }

    private fun handleLiveData(view: View) {
        viewModel.creationSongSuccess.observeSafe(this) {
            materialDialogComponentImpl.dismissDialog()
            if (it != null && it == true) {
                fragmentManager?.popBackStack()
            } else if (it != null && it == false) {
                if (viewModel.errorThrowable != null) {
//                    errorRendererComponent.requestRenderError(
//                        viewModel.errorThrowable as Throwable,
//                        ErrorRendererComponentImpl.ERROR_DISPLAY_MODE_SNACKBAR,
//                        view
//                    )
                }
                fragmentManager?.popBackStack()
            }
        }

        viewModel.creationSongNotLaunch.observeSafe(this) {
            materialDialogComponentImpl.dismissDialog()
            if (it != null && it == true) {
                if (viewModel.errorThrowable != null) {
//                    errorRendererComponent.requestRenderError(
//                        Exception(getString(R.string.error_field_not_filled)),
//                        ErrorRendererComponentImpl.ERROR_DISPLAY_MODE_SNACKBAR,
//                        view
//                    )
                }
            }
        }
    }

    private fun handleClickCreateSong() {
        fragment_user_song_creation_validation.setOnClickListener {
            materialDialogComponentImpl.showProgressDialog(
                getString(R.string.dialog_creation_song_title),
                getString(R.string.dialog_creation_song_content),
                R.color.colorPrimary
            )

            val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val instrumentMode = InstrumentModeUtils.getIntValueFromInstrumentMode(
                prefs.getString(
                    SharedPrefsManagerImpl.CURRENT_INSTRUMENT_MODE,
                    SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR
                )
            ).toString()
            viewModel.checkInformationAndValidateCreation(
                fragment_user_song_creation_name.text.toString(),
                fragment_user_song_creation_artist.text.toString(),
                instrumentMode
            )
        }
    }
}