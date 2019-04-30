package thomas.guitartrainingkotlin.presentation.fragment.exercise

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_exercise_mode.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.extension.ActivityExtensions
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.extension.setSupportActionBar
import thomas.guitartrainingkotlin.presentation.fragment.BaseExerciseFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.viewmodel.exercise.ExerciseModeViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.shared.ProgramSharedViewModel

class ExerciseModeFragment : BaseExerciseFragment<ExerciseModeViewModel>() {

    override val viewModelClass = ExerciseModeViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_exercise_mode

    private var items: Int = 0
    private var navHost: View? = null
    private var mSelectedItem: String? = null

    private lateinit var sharedViewModel: ProgramSharedViewModel

    companion object {
        const val NB_MODES = 7
    }

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
        initiateViewModelObservers()
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
        activity?.setSupportActionBar(fragment_exercise_mode_toolbar, ActivityExtensions.DISPLAY_UP)
        fragment_exercise_mode_toolbar.title = nameProgram
    }

    private fun initiateView() {

        setDurationUI(fragment_exercise_mode_duration, fragment_exercise_mode_duration_left)

        fragment_exercise_mode_button_choice_mode.setOnClickListener {
            showSimpleChoiceDialog()
        }
        fragment_exercise_mode_random_selection.setOnClickListener {
            viewModel.getRandomValue()
        }
        fragment_exercise_mode_button_start_exercise.setOnClickListener {
            launchTimer(fragment_exercise_mode_duration_left)
        }
        fragment_exercise_mode_next_button.setOnClickListener {
            startNextExercise()
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.finishRandomLiveEvent.observeSafe(this) { modeValue ->
            val modesArray = this.resources.getStringArray(R.array.list_modes)
            mSelectedItem = modesArray[modeValue]
            displaySelectedChoice(mSelectedItem)
        }
    }

    private fun showSimpleChoiceDialog() {
        val title = R.string.exercise_mode_dialog_choice_mode_text
        items = R.array.list_modes

        dialogComponent.displaySingleListChoiceDialog(
            title,
            items,
            android.R.string.ok,
            onPositive = { selectedItem ->
                displaySelectedChoice(selectedItem)
            })
    }

    private fun displaySelectedChoice(selectedItem: String?) {
        if (selectedItem != null && selectedItem.isNotEmpty()) {
            fragment_exercise_mode_button_choice_mode.text = selectedItem
        }
    }
}