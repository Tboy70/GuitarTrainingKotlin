package thomas.guitartrainingkotlin.presentation.fragment.exercise

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_exercise_scale.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.extension.ActivityExtensions
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.extension.setSupportActionBar
import thomas.guitartrainingkotlin.presentation.fragment.BaseExerciseFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.viewmodel.exercise.ExerciseScaleViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.shared.ProgramSharedViewModel

class ExerciseScaleFragment : BaseExerciseFragment<ExerciseScaleViewModel>() {

    override val viewModelClass = ExerciseScaleViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_exercise_scale

    private var items: Int = 0
    private var navHost: View? = null
    private var mSelectedItem: String? = null

    private lateinit var sharedViewModel: ProgramSharedViewModel

    companion object {
        const val NOTE_SELECTION = 1
        const val SCALE_SELECTION = 2
    }

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
        activity?.setSupportActionBar(fragment_exercise_scale_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateView() {

        setDurationUI(fragment_exercise_scale_duration, fragment_exercise_scale_duration_left)

        fragment_exercise_scale_button_choice_note.setOnClickListener {
            showSimpleChoiceDialog(NOTE_SELECTION)
        }
        fragment_exercise_scale_button_choice_mode.setOnClickListener {
            showSimpleChoiceDialog(SCALE_SELECTION)
        }
        fragment_exercise_scale_random_selection.setOnClickListener {
            viewModel.getRandomValue()
        }
        fragment_exercise_scale_button_start_exercise.setOnClickListener {
            launchTimer(fragment_exercise_scale_duration_left)
        }
        fragment_exercise_scale_next_button.setOnClickListener {
            startNextExercise()
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.finishRandomLiveEvent.observeSafe(this) { noteTonePair ->
            val notesArray = this.resources.getStringArray(R.array.list_notes)
            mSelectedItem = notesArray[noteTonePair.first]
            displaySelectedChoice(
                mSelectedItem,
                NOTE_SELECTION
            )

            val scalesArray = this.resources.getStringArray(R.array.list_scales)
            mSelectedItem = scalesArray[noteTonePair.second]
            displaySelectedChoice(
                mSelectedItem,
                SCALE_SELECTION
            )
        }
    }

    private fun showSimpleChoiceDialog(typeSelection: Int) {
        var title = 0
        when (typeSelection) {
            NOTE_SELECTION -> {
                title = R.string.exercise_scale_dialog_choice_note_text
                items = R.array.list_notes
            }
            SCALE_SELECTION -> {
                title = R.string.exercise_scale_dialog_choice_mode_text
                items = R.array.list_scales
            }
            else -> {
                items = 0
            }
        }

        dialogComponent.displaySingleListChoiceDialog(
            title,
            items,
            android.R.string.ok,
            onPositive = { selectedItem ->
                displaySelectedChoice(selectedItem, typeSelection)
            })
    }

    private fun displaySelectedChoice(selectedItem: String?, typeSelection: Int) {
        if (selectedItem != null && selectedItem.isNotEmpty()) {
            when (typeSelection) {
                NOTE_SELECTION -> fragment_exercise_scale_button_choice_note.text = selectedItem
                SCALE_SELECTION -> fragment_exercise_scale_button_choice_mode.text = selectedItem
            }
        }
    }
}