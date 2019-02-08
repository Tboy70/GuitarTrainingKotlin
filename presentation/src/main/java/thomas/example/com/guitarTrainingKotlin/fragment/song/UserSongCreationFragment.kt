package thomas.example.com.guitarTrainingKotlin.fragment.song

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_user_song_creation.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.DialogComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.extension.*
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.viewmodel.song.UserSongCreationViewModel
import javax.inject.Inject

class UserSongCreationFragment : BaseFragment<UserSongCreationViewModel>() {

    override val viewModelClass = UserSongCreationViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_song_creation

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var dialogComponent: DialogComponentImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateToolbar()
        initiateView()
        initiateViewModelObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(true)
        activity?.setSupportActionBar(fragment_user_song_creation_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateView() {
        fragment_user_song_creation_validation.setOnClickListener {
            dialogComponent.displayDualChoiceDialog(
                R.string.dialog_creation_song_title,
                R.string.dialog_creation_song_content,
                android.R.string.yes,
                android.R.string.no,
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