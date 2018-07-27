package thomas.example.com.guitarTrainingKotlin.fragment.user

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user_programs_list.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserProgramsListAdapter
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserProgramsListAdapterListener
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.viewmodel.UserProgramsListViewModel
import thomas.example.com.model.Program
import javax.inject.Inject

class UserProgramsListFragment : BaseFragment(), UserProgramsListAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userProgramsListViewModel: UserProgramsListViewModel

    @Inject
    lateinit var userProgramsListAdapter: UserProgramsListAdapter

    private lateinit var idUser: String

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_user_programs_list, container, false)

        recyclerView = rootView.findViewById(R.id.fragment_user_programs_list_recycler_view)
        swipeRefreshLayout = rootView.findViewById(R.id.fragment_user_programs_list_swipe_refresh_layout)

        userProgramsListViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserProgramsListViewModel::class.java)

        userProgramsListAdapter.setUserProgramsListAdapter(this)

        initRecyclerView()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.idUser = userProgramsListViewModel.getIdUser(activity!!)

        userProgramsListViewModel.finishRetrievePrograms.observe(this, Observer<Boolean> {
            if (it == true) {
                displayRetrievedPrograms(userProgramsListViewModel)
            }
        })

        userProgramsListViewModel.refreshList.observe(this, Observer<Boolean> {
            swipeRefreshLayout.isRefreshing = it == true && !swipeRefreshLayout.isRefreshing
        })
    }

    override fun onStart() {
        super.onStart()
        userProgramsListViewModel.retrieveProgramsListByUserId(idUser)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)

        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
        recyclerView.adapter = userProgramsListAdapter

        swipeRefreshLayout.setOnRefreshListener { userProgramsListViewModel.retrieveProgramsListByUserId(idUser) }
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
    }

    private fun displayRetrievedPrograms(userProgramsListViewModel: UserProgramsListViewModel) {
        val userPrograms: List<Program> = userProgramsListViewModel.userPrograms

        userProgramsListAdapter.updateProgramsList(userPrograms)
        if (userPrograms.isEmpty()) {
            fragment_user_programs_list_no_program_placeholder.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            fragment_user_programs_list_no_program_placeholder.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onProgramClick(idProgram: String) {
        val intent = Intent(activity, UserProgramActivity::class.java)
        intent.putExtra(ConstValues.ID_PROGRAM, idProgram)
        activity?.startActivity(intent)
    }
}