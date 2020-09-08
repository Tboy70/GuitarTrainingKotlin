package thomas.guitartrainingkotlin.presentation.fragment.song

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_song_update.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.component.DialogComponentImpl
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.view.datawrapper.SongViewDataWrapper
import thomas.guitartrainingkotlin.presentation.viewmodel.song.UserSongUpdateViewModel
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class UserSongUpdateFragment : Fragment(R.layout.fragment_user_song_update) {

    @Inject
    lateinit var dialogComponent: DialogComponentImpl

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    private var navHost: View? = null

    private val viewModel by viewModels<UserSongUpdateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            it.intent.extras?.let { bundle ->
                bundle.getString(ConstValues.ID_SONG)?.let { idSong ->
                    viewModel.setIdSong(idSong)
                }
            }
            navHost = it.findViewById(R.id.user_song_nav_host_fragment) as View
        }

        viewModel.getSongById()

        initiateToolbar()
        initiateView()
        initiateViewModelObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navHost?.let { view ->
                    Navigation.findNavController(view).navigateUp()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(true)
        activity?.setSupportActionBar(
            fragment_user_song_update_toolbar,
            ActivityExtensions.DISPLAY_UP
        )
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