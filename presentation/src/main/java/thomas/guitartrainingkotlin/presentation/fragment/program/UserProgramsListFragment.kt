package thomas.guitartrainingkotlin.presentation.fragment.program

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_programs_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.ProgramCreationActivity
import thomas.guitartrainingkotlin.presentation.activity.UserProgramActivity
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.extension.gone
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.extension.show
import thomas.guitartrainingkotlin.presentation.ui.animation.RevealAnimation
import thomas.guitartrainingkotlin.presentation.ui.adapter.UserProgramsListAdapter
import thomas.guitartrainingkotlin.presentation.ui.animation.MaterialMotionAnimation
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

        MaterialMotionAnimation.postponeEnterTransition(this, view)

        initiateView()
        initiateViewModelObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.retrieveProgramsListByUserId()
    }

    private fun initiateView() {
        fragment_user_programs_list_recycler_view.adapter = userProgramsListAdapter

        fragment_user_programs_list_swipe_refresh_layout.setOnRefreshListener {
            viewModel.retrieveProgramsListByUserId()
        }
        fragment_user_programs_list_swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)

        userProgramsListAdapter.onProgramSelectedListener = { programId, startView ->
            activity?.let { activity ->
                val intent = Intent(activity, UserProgramActivity::class.java)
                    .putExtra(ConstValues.ID_PROGRAM, programId)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    startView,
                    programId
                )
                activity.startActivity(intent, options.toBundle())
            }
        }

        fragment_user_programs_floating_action_button.setOnClickListener {
            startRevealActivity(it)
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
            fragment_user_programs_list_no_program_placeholder.show()
            fragment_user_programs_list_recycler_view.gone()
        } else {
            fragment_user_programs_list_no_program_placeholder.gone()
            fragment_user_programs_list_recycler_view.show()
        }
    }

    private fun startRevealActivity(v: View) {
        activity?.let {
            //calculates the center of the View v you are passing
            val revealX = (v.x + v.width / 2).toInt()
            val revealY = (v.y + v.height / 2).toInt()

            //create an intent, that launches the second activity and pass the x and y coordinates
            val intent = Intent(it, ProgramCreationActivity::class.java)
            intent.putExtra(RevealAnimation.EXTRA_CIRCULAR_REVEAL_X, revealX)
            intent.putExtra(RevealAnimation.EXTRA_CIRCULAR_REVEAL_Y, revealY)

            //just start the activity as an shared transition, but set the options bundle to null
            ActivityCompat.startActivity(it, intent, null)

            //to prevent strange behaviours override the pending transitions
            it.overridePendingTransition(0, 0)
        }
    }
}