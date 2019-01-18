package thomas.example.com.guitarTrainingKotlin.fragment.song

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_user_songs_list.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserSongActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserSongsListAdapter
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserSongsListAdapterListener
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.SongViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserSongsListViewModel
import javax.inject.Inject

class UserSongsListFragment : BaseFragment<UserSongsListViewModel>(), UserSongsListAdapterListener {

    override val viewModelClass = UserSongsListViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_songs_list

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var userSongsListAdapter: UserSongsListAdapter

    private lateinit var idUser: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userSongsListAdapter.setUserSongsListAdapter(this)

        initRecyclerView()

        this.idUser = viewModel.getIdUser(activity as UserPanelActivity)

        viewModel.userSongs.observeSafe(this) {
            displayRetrievedSongs(it)
        }

        viewModel.viewState.observeSafe(this) {
            fragment_user_songs_list_swipe_refresh_layout.isRefreshing = it.refreshList == true &&
                    !fragment_user_songs_list_swipe_refresh_layout.isRefreshing
        }

        viewModel.errorEvent.observeSafe(this) {
            if (it.ERROR_TRIGGERED && viewModel.errorThrowable != null) {
                errorRendererComponent.requestRenderError(
                    viewModel.errorThrowable as Throwable,
                    ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR,
                    view
                )
            }
        }

        handleAddNewSong()
    }

    override fun onStart() {
        super.onStart()
        (activity as UserPanelActivity).setToolbar((activity as UserPanelActivity).getString(R.string.user_panel_navigation_drawer_songs))
        viewModel.retrieveSongsListByUserId(idUser)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)

        fragment_user_songs_list_recycler_view.layoutManager = layoutManager
        fragment_user_songs_list_recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                layoutManager.orientation
            )
        )
        fragment_user_songs_list_recycler_view.adapter = userSongsListAdapter

        fragment_user_songs_list_swipe_refresh_layout.setOnRefreshListener {
            viewModel.retrieveSongsListByUserId(idUser)
        }
        fragment_user_songs_list_swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
    }

    private fun handleAddNewSong() {
        fragment_user_songs_floating_action_button.setOnClickListener {
            val host = activity?.findViewById(R.id.user_panel_nav_host_fragment) as View
            findNavController(host).navigate(R.id.add_song, null, null)
        }
    }

    private fun displayRetrievedSongs(userSongsList: List<SongViewDataWrapper>) {
        val userSongs: List<SongViewDataWrapper> = userSongsList

        userSongsListAdapter.updateSongsList(userSongs)
        if (userSongs.isEmpty()) {
            fragment_user_songs_list_no_program_placeholder.visibility = View.VISIBLE
            fragment_user_songs_list_recycler_view.visibility = View.GONE
        } else {
            fragment_user_songs_list_no_program_placeholder.visibility = View.GONE
            fragment_user_songs_list_recycler_view.visibility = View.VISIBLE
        }
    }

    override fun onSongClick(idSong: String) {
        val intent = Intent(activity, UserSongActivity::class.java)
        intent.putExtra(ConstValues.ID_SONG, idSong)
        activity?.startActivity(intent)
    }
}