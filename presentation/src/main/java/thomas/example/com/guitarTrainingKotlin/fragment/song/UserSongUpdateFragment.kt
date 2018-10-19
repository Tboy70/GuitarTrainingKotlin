package thomas.example.com.guitarTrainingKotlin.fragment.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_user_song_update.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.SongViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.viewmodel.song.UserSongUpdateViewModel
import javax.inject.Inject

class UserSongUpdateFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userSongUpdateViewModel: UserSongUpdateViewModel

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    private var songViewDataWrapper: SongViewDataWrapper? = null

    companion object {
        const val SONG_OBJECT_WRAPPER_KEY =
            "thomas.example.com.guitarTrainingKotlin.fragment.song.SONG_OBJECT_WRAPPER_KEY"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_song_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userSongUpdateViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserSongUpdateViewModel::class.java)

        val bundle = arguments
        if (bundle != null) {
            if (bundle.containsKey(UserSongUpdateFragment.SONG_OBJECT_WRAPPER_KEY)) {
                songViewDataWrapper =
                        bundle.getSerializable(UserSongUpdateFragment.SONG_OBJECT_WRAPPER_KEY) as SongViewDataWrapper
            }
        }

        handleLiveData(view)
        initEditText()
        handleClickValidateUpdateButton()
    }

    private fun handleLiveData(view: View) {
        userSongUpdateViewModel.updateSongSuccess.observeSafe(this) {
            materialDialogComponent.dismissDialog()
            if (it != null && it == true) {
                activity?.finish()
            } else if (it != null && it == false) {
                if (userSongUpdateViewModel.errorThrowable != null) {
                    errorRendererComponent.requestRenderError(
                            userSongUpdateViewModel.errorThrowable as Throwable,
                            ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR,
                            view
                    )
                }
            }
        }
    }

    private fun initEditText() {
        fragment_user_song_update_name.setText(songViewDataWrapper?.getTitleSong())
        fragment_user_song_update_description.setText(songViewDataWrapper?.getArtistSong())
    }

    private fun handleClickValidateUpdateButton() {
        fragment_user_song_update_validate_button.setOnClickListener {

            materialDialogComponent.showMultiChoiceDialog(
                    getString(R.string.dialog_update_song_title),
                    getString(R.string.dialog_update_song_confirm_content),
                    R.color.colorPrimary,
                    object : MultipleChoiceMaterialDialogListener {
                        override fun onYesSelected() {
                            materialDialogComponent.showProgressDialog(
                                    getString(R.string.dialog_update_song_title),
                                    getString(R.string.dialog_update_song_content),
                                    R.color.colorPrimary
                            )

                            userSongUpdateViewModel.checkInformationAndValidateUpdate(
                                    songViewDataWrapper?.getIdSong()!!, // TODO : Check that
                                    fragment_user_song_update_name.text.toString(),
                                    fragment_user_song_update_description.text.toString()
                            )
                        }
                    })
        }
    }

}