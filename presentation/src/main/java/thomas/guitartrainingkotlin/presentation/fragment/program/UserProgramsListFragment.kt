package thomas.guitartrainingkotlin.presentation.fragment.program

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_programs_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.ProgramCreationActivity
import thomas.guitartrainingkotlin.presentation.activity.UserProgramActivity
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.ui.adapter.UserProgramsListAdapter
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.viewmodel.user.UserProgramsListViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserProgramsListFragment : Fragment(R.layout.fragment_user_programs_list) {

    @Inject
    lateinit var userProgramsListAdapter: UserProgramsListAdapter

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    private val viewModel by viewModels<UserProgramsListViewModel>()

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
        viewModel.retrieveProgramsListByUserId()
    }

    private fun initiateView() {
        fragment_user_programs_list_recycler_view.adapter = userProgramsListAdapter
        fragment_user_programs_list_recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )

        fragment_user_programs_list_swipe_refresh_layout.setOnRefreshListener {
            viewModel.retrieveProgramsListByUserId()
        }
        fragment_user_programs_list_swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)

        userProgramsListAdapter.onProgramSelectedListener = { programId ->
            activity?.let { activity ->
                activity.startActivity(
                    Intent(activity, UserProgramActivity::class.java)
                        .putExtra(ConstValues.ID_PROGRAM, programId)
                )
            }
        }

        fragment_user_programs_floating_action_button.setOnClickListener {
            startActivity(Intent(activity, ProgramCreationActivity::class.java))
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.retrieveUserProgramLiveData.observeSafe(this) {
            displayRetrievedPrograms(it)
        }

        viewModel.viewState.observeSafe(this) {
            fragment_user_programs_list_swipe_refresh_layout.isRefreshing = it.loading
        }

        viewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }
    }

    private fun displayRetrievedPrograms(userProgramList: List<ProgramViewDataWrapper>) {
        userProgramsListAdapter.updateProgramList(userProgramList)
        if (userProgramList.isEmpty()) {
            fragment_user_programs_list_no_program_placeholder.visibility = View.VISIBLE
            fragment_user_programs_list_recycler_view.visibility = View.GONE
        } else {
            fragment_user_programs_list_no_program_placeholder.visibility = View.GONE
            fragment_user_programs_list_recycler_view.visibility = View.VISIBLE
        }
    }
}