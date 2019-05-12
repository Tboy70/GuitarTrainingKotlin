package thomas.guitartrainingkotlin.presentation.fragment.exercise

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_exercise_skip_string.*
import kotlinx.android.synthetic.main.view_action_exercise.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.extension.ActivityExtensions
import thomas.guitartrainingkotlin.presentation.extension.setSupportActionBar
import thomas.guitartrainingkotlin.presentation.fragment.BaseExerciseFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.viewmodel.exercise.ExerciseSkipStringViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.shared.ProgramSharedViewModel

class ExerciseSkipStringFragment : BaseExerciseFragment<ExerciseSkipStringViewModel>() {

    override val viewModelClass = ExerciseSkipStringViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_exercise_skip_string

    private var navHost: View? = null

    private lateinit var sharedViewModel: ProgramSharedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            navHost = it.findViewById(R.id.program_nav_host_fragment) as View
            sharedViewModel = ViewModelProviders.of(it, viewModelFactory).get(ProgramSharedViewModel::class.java)
        }

        nameProgram = arguments?.getString(NAME_PROGRAM) ?: ""
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
        activity?.setSupportActionBar(fragment_exercise_skip_string_toolbar, ActivityExtensions.DISPLAY_UP)
        fragment_exercise_skip_string_toolbar.title = nameProgram
    }

    private fun initiateView() {

        setDurationUI(fragment_exercise_skip_string_duration, fragment_exercise_skip_string_duration_left)

        view_action_exercise_start.setOnClickListener {
            launchTimer(fragment_exercise_skip_string_duration_left)
        }
        view_action_exercise_next.setOnClickListener {
            startNextExercise()
        }
    }
}