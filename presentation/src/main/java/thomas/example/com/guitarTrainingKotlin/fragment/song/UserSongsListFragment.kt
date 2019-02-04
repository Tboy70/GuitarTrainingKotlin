package thomas.example.com.guitarTrainingKotlin.fragment.song

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_user_songs_list.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.SongCreationActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserSongActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserSongsListAdapter
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.view.datawrapper.SongViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserSongsListViewModel
import javax.inject.Inject

class UserSongsListFragment : BaseFragment<UserSongsListViewModel>() {

    override val viewModelClass = UserSongsListViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_songs_list

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var userSongsListAdapter: UserSongsListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            viewModel.setUserId(it.getString(ConstValues.USER_ID))
        }

        initiateToolbar()
        initiateView()
        initiateViewModelObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.retrieveSongListByUserId()
    }

    private fun initiateToolbar() {
        (activity as UserPanelActivity).setToolbar((activity as UserPanelActivity).getString(R.string.user_panel_navigation_drawer_songs))
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