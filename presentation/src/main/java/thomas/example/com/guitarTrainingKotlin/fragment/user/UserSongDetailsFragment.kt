package thomas.example.com.guitarTrainingKotlin.fragment.user

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user_song_details.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.ProgramActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserSongActivity
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.SongObjectWrapper
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserSongDetailsViewModel
import javax.inject.Inject

class UserSongDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userSongDetailsViewModel: UserSongDetailsViewModel

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    private lateinit var idSong: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_user_song_details, container, false)

        userSongDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserSongDetailsViewModel::class.java)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireActivity().intent.extras
        if (bundle != null) {
            if (bundle.containsKey(ConstValues.ID_SONG)) {
                idSong = bundle.getString(ConstValues.ID_SONG)
            }
        }

        handleLiveData()
        handleStartSong()
        handleUpdateSong()
        handleRemoveSong()
    }

    override fun onStart() {
        super.onStart()
        materialDialogComponent.showProgressDialog(getString(R.string.dialog_details_song_title), getString(R.string.dialog_details_song_content), R.color.colorPrimary)
        userSongDetailsViewModel.getSongById(idSong)
    }

    private fun handleLiveData() {
        userSongDetailsViewModel.finishRetrieveSongForDetails.observe(this, Observer<Boolean> {
            if (it == true) {
                val userSongObjectWrapper = userSongDetailsViewModel.userSongObjectWrapper
                displayInformation(userSongObjectWrapper)
                userSongDetailsViewModel.finishRetrieveSongForDetails.removeObservers(this)
            }
        })

        userSongDetailsViewModel.finishLoading.observe(this, Observer<Boolean> {
            if (it != null) {
                materialDialogComponent.dismissDialog()
            }
        })

        userSongDetailsViewModel.finishSongDeletion.observe(this, Observer<Boolean> {
            if (it != null && it == true) {
                activity?.finish()
            }
        })
    }

    private fun displayInformation(userSongObjectWrapper: SongObjectWrapper) {
        val titleSong = userSongObjectWrapper.song.titleSong
        val artistSong = userSongObjectWrapper.song.artistSong

        setToolbar(titleSong)

        fragment_user_song_details_name.text = titleSong
        fragment_user_song_details_description.text = artistSong
    }

    private fun setToolbar(nameSong: String) {
        if (activity is UserSongActivity) {
            (activity as UserSongActivity).setToolbar(nameSong)
        }
    }

    private fun handleStartSong() {
        fragment_user_song_details_start_button.setOnClickListener {
            val intent = Intent(activity, ProgramActivity::class.java)
            intent.putExtra(ConstValues.ID_SONG, idSong)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun handleUpdateSong() {
        fragment_user_song_details_update_button.setOnClickListener {
            //TODO : Update song
//            val bundle = Bundle()
//            bundle.putSerializable(UserProgramUpdateFragment.PROGRAM_OBJECT_WRAPPER_KEY, userSongDetailsViewModel.userProgramObjectWrapper)
//
//            val host = activity?.supportFragmentManager?.findFragmentById(R.id.user_program_nav_host_fragment) as NavHostFragment
//            NavHostFragment.findNavController(host).navigate(R.id.user_program_update, bundle, null)
        }
    }

    private fun handleRemoveSong() {
        fragment_user_song_details_remove_button.setOnClickListener {
            materialDialogComponent.showMultiChoiceDialog(getString(R.string.dialog_remove_song_title), getString(R.string.dialog_remove_song_confirm_content), R.color.colorPrimary, object : MultipleChoiceMaterialDialogListener {
                override fun onYesSelected() {
                    materialDialogComponent.showProgressDialog(getString(R.string.dialog_remove_song_title), getString(R.string.dialog_remove_song_content), R.color.colorPrimary)
                    userSongDetailsViewModel.removeSong(idSong)
                }
            })
        }
    }
}