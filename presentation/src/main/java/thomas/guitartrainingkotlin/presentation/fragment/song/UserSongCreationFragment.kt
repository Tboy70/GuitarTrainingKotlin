package thomas.guitartrainingkotlin.presentation.fragment.song

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_song_creation.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.ErrorRendererComponent
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.viewmodel.song.UserSongCreationViewModel
import javax.inject.Inject

@FlowPreview
@AndroidEntryPoint
@ExperimentalCoroutinesApi
class UserSongCreationFragment : Fragment(R.layout.fragment_user_song_creation) {

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var dialogComponent: DialogComponent

    private val viewModel by viewModels<UserSongCreationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateToolbar()
        initiateView()
        initiateViewModelObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(true)
        activity?.setSupportActionBar(
            fragment_user_song_creation_toolbar,
            ActivityExtensions.DISPLAY_UP
        )
    }

    private fun initiateView() {
        fragment_user_song_creation_validation.setOnClickListener {
            dialogComponent.displayDualChoiceDialog(
                R.string.dialog_creation_song_title,
                R.string.dialog_creation_song_content,
                android.R.string.ok,
                android.R.string.cancel,
                onPositive = {
                    viewModel.checkInformationAndValidateCreation(
                        fragment_user_song_creation_name.text.toString(),
                        fragment_user_song_creation_artist.text.toString()
                    )
                },
                onNegative = {}
            )
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.songCreatedLiveEvent.observeSafe(this) {
            if (it != null && it == true) {
                activity?.finish()
            }
        }

        viewModel.informationNotRightLiveEvent.observeSafe(this) {
            if (it) {
                context?.let { context ->
                    errorRendererComponent.displayErrorString(context.getString(R.string.error_wrong_info))
                }
            }
        }

        viewModel.viewState.observeSafe(this) {
            if (it.loading) {
                fragment_user_song_creation_progress_bar.show()
            } else {
                fragment_user_song_creation_progress_bar.gone()
            }
        }

        viewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }
    }
}