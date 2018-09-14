package thomas.example.com.guitarTrainingKotlin.fragment.user

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user_songs_list.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserSongsListAdapter
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserSongsListAdapterListener
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserSongsListViewModel
import thomas.example.com.model.Song
import javax.inject.Inject

class UserSongsListFragment : BaseFragment(), UserSongsListAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userSongsListViewModel: UserSongsListViewModel

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

        userSongsListViewModel.finishRetrieveSongs.observe(this, Observer<Boolean> {
            if (it == true) {
                displayRetrievedSongs(userSongsListViewModel)
            }
        })

        userSongsListViewModel.refreshList.observe(this, Observer<Boolean> {
            swipeRefreshLayout.isRefreshing = it == true && !swipeRefreshLayout.isRefreshing
        })
    }

    override fun onStart() {
        super.onStart()
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

    private fun displayRetrievedSongs(userSongsListViewModel: UserSongsListViewModel) {
        val userSongs: List<Song> = userSongsListViewModel.userSongs

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}