package thomas.example.com.guitarTrainingKotlin.fragment.song

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_user_song_update.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.SongViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.viewmodel.song.UserSongUpdateViewModel
import javax.inject.Inject

class UserSongUpdateFragment : BaseFragment<UserSongUpdateViewModel>() {

    override val viewModelClass = UserSongUpdateViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_song_update

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    private var songViewDataWrapper: SongViewDataWrapper? = null

    companion object {
        const val SONG_OBJECT_WRAPPER_KEY =
            "thomas.example.com.guitarTrainingKotlin.fragment.song.SONG_OBJECT_WRAPPER_KEY"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        viewModel.updateSongSuccess.observeSafe(this) {
            materialDialogComponent.dismissDialog()
            if (it != null && it == true) {
                activity?.finish()
            } else if (it != null && it == false) {
                if (viewModel.errorThrowable != null) {
//                    errorRendererComponent.requestRenderError(
//                        viewModel.errorThrowable as Throwable,
//                        ErrorRendererComponentImpl.ERROR_DISPLAY_MODE_SNACKBAR,
//                        view
//                    )
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

                        viewModel.checkInformationAndValidateUpdate(
                            songViewDataWrapper?.getIdSong()!!, // TODO : Check that
                            fragment_user_song_update_name.text.toString(),
                            fragment_user_song_update_description.text.toString()
                        )
                    }
                })
        }
    }

}