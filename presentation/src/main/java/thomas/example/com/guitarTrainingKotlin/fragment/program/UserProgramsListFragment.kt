package thomas.example.com.guitarTrainingKotlin.fragment.program

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
import kotlinx.android.synthetic.main.fragment_user_programs_list.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserProgramsListAdapter
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserProgramsListAdapterListener
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserProgramsListViewModel
import thomas.example.com.model.Program
import javax.inject.Inject

class UserProgramsListFragment : BaseFragment(), UserProgramsListAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userProgramsListViewModel: UserProgramsListViewModel

    @Inject
    lateinit var userProgramsListAdapter: UserProgramsListAdapter

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    private lateinit var idUser: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_user_programs_list, container, false)

        recyclerView = rootView.findViewById(R.id.fragment_user_programs_list_recycler_view)
        swipeRefreshLayout = rootView.findViewById(R.id.fragment_user_programs_list_swipe_refresh_layout)

        userProgramsListViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(UserProgramsListViewModel::class.java)

        userProgramsListAdapter.setUserProgramsListAdapter(this)

        initRecyclerView()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.idUser = userProgramsListViewModel.getIdUser(activity as UserPanelActivity)

        userProgramsListViewModel.finishRetrievePrograms.observeSafe(this) {
            if (it == true) {
                displayRetrievedPrograms(userProgramsListViewModel)
            } else {
                errorRendererComponent.requestRenderError(
                    userProgramsListViewModel.errorThrowable as Throwable,
                    ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR,
                    view
                )
            }
        }

        userProgramsListViewModel.refreshList.observeSafe(this) {
            swipeRefreshLayout.isRefreshing = it == true && !swipeRefreshLayout.isRefreshing
        }

        handleAddNewProgramButton()
    }

    override fun onStart() {
        super.onStart()
        (activity as UserPanelActivity).setToolbar((activity as UserPanelActivity).getString(R.string.user_panel_navigation_drawer_programs))
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

    private fun handleAddNewProgramButton() {
        fragment_user_programs_floating_action_button.setOnClickListener {
            val host = activity?.supportFragmentManager?.findFragmentById(R.id.user_panel_nav_host_fragment) as View
            findNavController(host).navigate(R.id.add_program, null, null)
        }
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