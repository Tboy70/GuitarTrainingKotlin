package thomas.example.com.guitarTrainingKotlin.fragment.program

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_user_programs_list.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserProgramsListAdapter
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserProgramsListAdapterListener
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.ProgramViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserProgramsListViewModel
import javax.inject.Inject

class UserProgramsListFragment : BaseFragment<UserProgramsListViewModel>(), UserProgramsListAdapterListener {

    override val viewModelClass = UserProgramsListViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_programs_list

    @Inject
    lateinit var userProgramsListAdapter: UserProgramsListAdapter

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    private lateinit var idUser: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
                ViewModelProviders.of(this, viewModelFactory).get(viewModel::class.java)

        userProgramsListAdapter.setUserProgramsListAdapter(this)

        initRecyclerView()

        this.idUser = viewModel.getIdUser(activity as UserPanelActivity)

        viewModel.userPrograms.observeSafe(this) {
            displayRetrievedPrograms(it)
        }

        viewModel.viewState.observeSafe(this) {
            fragment_user_programs_list_swipe_refresh_layout.isRefreshing = it.refreshList == true &&
                    !fragment_user_programs_list_swipe_refresh_layout.isRefreshing
        }

        viewModel.errorEvent.observeSafe(this) {
            if (it.ERROR_TRIGGERED && viewModel.errorThrowable != null) {
//                errorRendererComponent.requestRenderError(
//                    viewModel.errorThrowable as Throwable,
//                    ErrorRendererComponentImpl.ERROR_DISPLAY_MODE_SNACKBAR,
//                    view
//                )
            }
        }

        handleAddNewProgramButton()
    }

    override fun onStart() {
        super.onStart()
        (activity as UserPanelActivity).setToolbar((activity as UserPanelActivity).getString(R.string.user_panel_navigation_drawer_programs))
        viewModel.retrieveProgramsListByUserId(idUser)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)

        fragment_user_programs_list_recycler_view.layoutManager = layoutManager
        fragment_user_programs_list_recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                layoutManager.orientation
            )
        )
        fragment_user_programs_list_recycler_view.adapter = userProgramsListAdapter

        fragment_user_programs_list_swipe_refresh_layout.setOnRefreshListener {
            viewModel.retrieveProgramsListByUserId(
                idUser
            )
        }
        fragment_user_programs_list_swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
    }

    private fun handleAddNewProgramButton() {
        fragment_user_programs_floating_action_button.setOnClickListener {
            val host = activity?.findViewById(R.id.user_panel_nav_host_fragment) as View
            findNavController(host).navigate(R.id.add_program, null, null)
        }
    }

    private fun displayRetrievedPrograms(userProgramsList: List<ProgramViewDataWrapper>) {
        val userPrograms: List<ProgramViewDataWrapper> = userProgramsList

        userProgramsListAdapter.updateProgramsList(userProgramsList)
        if (userPrograms.isEmpty()) {
            fragment_user_programs_list_no_program_placeholder.visibility = View.VISIBLE
            fragment_user_programs_list_recycler_view.visibility = View.GONE
        } else {
            fragment_user_programs_list_no_program_placeholder.visibility = View.GONE
            fragment_user_programs_list_recycler_view.visibility = View.VISIBLE
        }
    }

    override fun onProgramClick(idProgram: String) {
        val intent = Intent(activity, UserProgramActivity::class.java)
        intent.putExtra(ConstValues.ID_PROGRAM, idProgram)
        activity?.startActivity(intent)
    }
}