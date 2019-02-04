package thomas.example.com.guitarTrainingKotlin.fragment.song

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_user_song_update.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.DialogComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.extension.*
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.view.datawrapper.SongViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.viewmodel.song.UserSongUpdateViewModel
import javax.inject.Inject

class UserSongUpdateFragment : BaseFragment<UserSongUpdateViewModel>() {

    override val viewModelClass = UserSongUpdateViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_song_update

    @Inject
    lateinit var dialogComponent: DialogComponentImpl

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            it.intent.extras?.let { bundle ->
                bundle.getString(ConstValues.ID_SONG)?.let { idSong ->
                    viewModel.setIdSong(idSong)
                }
            }
        }

        viewModel.getSongById()

        initiateToolbar()
        initiateView()
        initiateViewModelObservers()
    }

    private fun initiateToolbar() {
        activity?.setSupportActionBar(fragment_user_song_update_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateView() {
        fragment_user_song_update_validate_button.setOnClickListener {
            dialogComponent.displayDualChoiceDialog(
                R.string.dialog_update_song_title,
                R.string.dialog_update_song_confirm_content,
                android.R.string.yes,
                android.R.string.cancel,
                onPositive = {
                    viewModel.checkInformationAndValidateUpdate(
                        fragment_user_song_update_name.text.toString(),
                        fragment_user_song_update_description.text.toString()
                    )
                },
                onNegative = {}
            )
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.songRetrievedLiveData.observeSafe(this) {
            displaySongInformation(it)
        }

        viewModel.songUpdatedLiveEvent.observeSafe(this) {
            activity?.finish()

        }

        viewModel.viewState.observeSafe(this) {
            if (it.loading) {
                fragment_user_song_update_progress_bar.show()
            } else {
                fragment_user_song_update_progress_bar.gone()
            }
        }

        viewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }

    }

    private fun displaySongInformation(songViewDataWrapper: SongViewDataWrapper) {
        fragment_user_song_update_name.setText(songViewDataWrapper.getTitleSong())
        fragment_user_song_update_description.setText(songViewDataWrapper.getArtistSong())
    }

}