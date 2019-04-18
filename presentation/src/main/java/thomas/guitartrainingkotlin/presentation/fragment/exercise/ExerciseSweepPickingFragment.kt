package thomas.guitartrainingkotlin.presentation.fragment.exercise

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_exercise_sweep_picking.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.extension.ActivityExtensions
import thomas.guitartrainingkotlin.presentation.extension.setSupportActionBar
import thomas.guitartrainingkotlin.presentation.fragment.BaseExerciseFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.viewmodel.exercise.ExerciseSweepPickingViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.shared.ProgramSharedViewModel

class ExerciseSweepPickingFragment : BaseExerciseFragment<ExerciseSweepPickingViewModel>() {

    override val viewModelClass = ExerciseSweepPickingViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_exercise_sweep_picking

    private var navHost: View? = null

    private lateinit var sharedViewModel: ProgramSharedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            navHost = it.findViewById(R.id.program_nav_host_fragment) as View
            sharedViewModel = ViewModelProviders.of(it, viewModelFactory).get(ProgramSharedViewModel::class.java)
        }

        rankExercise = arguments?.getInt(RANK_EXERCISE) ?: ConstValues.CONST_ERROR
        durationExercise = arguments?.getInt(DURATION_EXERCISE) ?: ConstValues.CONST_ERROR

        initiateToolbar()
        initiateView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navHost?.let {
                    sharedViewModel.onBackPressed()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(true)
        activity?.setSupportActionBar(fragment_exercise_sweep_picking_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateView() {

        setDurationUI(fragment_exercise_sweep_picking_duration, fragment_exercise_sweep_picking_duration_left)

        fragment_exercise_sweep_picking_button_start_exercise.setOnClickListener {
            launchTimer(fragment_exercise_sweep_picking_duration_left)
        }
        fragment_exercise_sweep_picking_next_button.setOnClickListener {
            startNextExercise()
        }
    }
}