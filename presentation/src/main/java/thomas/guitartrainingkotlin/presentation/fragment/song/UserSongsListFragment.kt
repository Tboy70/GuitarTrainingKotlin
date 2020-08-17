package thomas.guitartrainingkotlin.presentation.fragment.song

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_songs_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.SongCreationActivity
import thomas.guitartrainingkotlin.presentation.activity.UserSongActivity
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.ui.adapter.UserSongsListAdapter
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.view.datawrapper.SongViewDataWrapper
import thomas.guitartrainingkotlin.presentation.viewmodel.user.UserSongsListViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserSongsListFragment : Fragment(R.layout.fragment_user_songs_list) {

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var userSongsListAdapter: UserSongsListAdapter

    private val viewModel by viewModels<UserSongsListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            viewModel.setUserId(it.getString(ConstValues.USER_ID))
        }

//        initiateToolbar()
        initiateView()
        initiateViewModelObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.retrieveSongListByUserId()
    }

    private fun initiateView() {

        fragment_user_songs_list_recycler_view.adapter = userSongsListAdapter
        fragment_user_songs_list_recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )

        fragment_user_songs_list_swipe_refresh_layout.setOnRefreshListener {
            viewModel.retrieveSongListByUserId()
        }
        fragment_user_songs_list_swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)

        userSongsListAdapter.onSongSelectedListener = { songId ->
            activity?.let { activity ->
                activity.startActivity(
                    Intent(activity, UserSongActivity::class.java)
                        .putExtra(ConstValues.ID_SONG, songId)
                )
            }
        }

        fragment_user_songs_floating_action_button.setOnClickListener {
            startActivity(Intent(activity, SongCreationActivity::class.java))
        }
    }

    private fun initiateViewModelObservers() {

        viewModel.retrieveUserSongsLiveData.observeSafe(this) {
            displayRetrievedSongs(it)
        }

        viewModel.viewState.observeSafe(this) {
            fragment_user_songs_list_swipe_refresh_layout.isRefreshing = it.loading
        }

        viewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }
    }

    private fun displayRetrievedSongs(userSongsList: List<SongViewDataWrapper>) {
        userSongsListAdapter.updateSongList(userSongsList)
        if (userSongsList.isEmpty()) {
            fragment_user_songs_list_no_program_placeholder.visibility = View.VISIBLE
            fragment_user_songs_list_recycler_view.visibility = View.GONE
        } else {
            fragment_user_songs_list_no_program_placeholder.visibility = View.GONE
            fragment_user_songs_list_recycler_view.visibility = View.VISIBLE
        }
    }
}