package thomas.example.com.guitarTrainingKotlin.fragment.song

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

class UserSongsListFragment : BaseFragment(), UserSongsListAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userSongsListViewModel: UserSongsListViewModel

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var userSongsListAdapter: UserSongsListAdapter

    private lateinit var idUser: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_user_songs_list, container, false)

        recyclerView = rootView.findViewById(R.id.fragment_user_songs_list_recycler_view)
        swipeRefreshLayout = rootView.findViewById(R.id.fragment_user_songs_list_swipe_refresh_layout)

        userSongsListViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserSongsListViewModel::class.java)

        userSongsListAdapter.setUserSongsListAdapter(this)

        initRecyclerView()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.idUser = userSongsListViewModel.getIdUser(activity as UserPanelActivity)

        userSongsListViewModel.userSongs.observeSafe(this) {
            displayRetrievedSongs(it)
        }

        userSongsListViewModel.viewState.observeSafe(this) {
            swipeRefreshLayout.isRefreshing = it.refreshList == true && !swipeRefreshLayout.isRefreshing
        }

        userSongsListViewModel.errorEvent.observeSafe(this) {
            if (it.ERROR_TRIGGERED && userSongsListViewModel.errorThrowable != null) {
                errorRendererComponent.requestRenderError(
                        userSongsListViewModel.errorThrowable as Throwable,
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
        userSongsListViewModel.retrieveSongsListByUserId(idUser)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)

        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
        recyclerView.adapter = userSongsListAdapter

        swipeRefreshLayout.setOnRefreshListener {
            userSongsListViewModel.retrieveSongsListByUserId(idUser)
        }
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
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
            recyclerView.visibility = View.GONE
        } else {
            fragment_user_songs_list_no_program_placeholder.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onSongClick(idSong: String) {
        val intent = Intent(activity, UserSongActivity::class.java)
        intent.putExtra(ConstValues.ID_SONG, idSong)
        activity?.startActivity(intent)
    }
}