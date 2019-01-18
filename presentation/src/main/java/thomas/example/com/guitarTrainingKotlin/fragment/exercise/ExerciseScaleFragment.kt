package thomas.example.com.guitarTrainingKotlin.fragment.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_exercise_scale.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.listener.SingleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.viewmodel.exercise.ExerciseScaleViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.factory.ViewModelFactory
import javax.inject.Inject

class ExerciseScaleFragment : AbstractExerciseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var exerciseScaleViewModel: ExerciseScaleViewModel

    private lateinit var items: MutableList<String>

    private var mSelectedItem: String? = null

    companion object {
        const val NOTE_SELECTION = 1
        const val SCALE_SELECTION = 2
        const val NB_NOTES = 12
        const val NB_SCALES = 3
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercise_scale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exerciseScaleViewModel = ViewModelProviders.of(this, viewModelFactory).get(ExerciseScaleViewModel::class.java)

        rankExercise = arguments?.getInt(AbstractExerciseFragment.RANK_EXERCISE) ?: ConstValues.CONST_ERROR
        durationExercise = arguments?.getInt(AbstractExerciseFragment.DURATION_EXERCISE) ?: ConstValues.CONST_ERROR

        handleClickNoteButton()
        handleClickModeButton()
        handleClickRandomButton()
        handleClickStartButton()
        handleClickNextButton()

        setDurationUI(fragment_exercise_scale_duration, fragment_exercise_scale_duration_left)
        setToolbar(R.string.toolbar_title_exercise_scale)

        exerciseScaleViewModel.finishRandom.observeSafe(this) {
            val notesArray = this.resources.getStringArray(R.array.list_notes)
            mSelectedItem = notesArray[exerciseScaleViewModel.scaleNoteValue]
            displaySelectedChoice(mSelectedItem, NOTE_SELECTION)

            val scalesArray = this.resources.getStringArray(R.array.list_scales)
            mSelectedItem = scalesArray[exerciseScaleViewModel.scaleToneValue]
            displaySelectedChoice(mSelectedItem, SCALE_SELECTION)
        }
    }

    private fun handleClickNoteButton() {
        fragment_exercise_scale_button_choice_note.setOnClickListener {
            showSimpleChoiceDialog(NOTE_SELECTION)
        }
    }

    private fun handleClickModeButton() {
        fragment_exercise_scale_button_choice_mode.setOnClickListener {
            showSimpleChoiceDialog(SCALE_SELECTION)
        }
    }

    private fun handleClickRandomButton() {
        fragment_exercise_scale_random_selection.setOnClickListener {
            exerciseScaleViewModel.getRandomValue()
        }
    }

    private fun handleClickStartButton() {
        fragment_exercise_scale_button_start_exercise.setOnClickListener {
            launchTimer(fragment_exercise_scale_duration_left)
        }
    }

    private fun handleClickNextButton() {
        fragment_exercise_scale_next_button.setOnClickListener {
            startNextExercise()
        }
    }

    private fun showSimpleChoiceDialog(typeSelection: Int) {
        var title: String = ConstValues.EMPTY_STRING
        when (typeSelection) {
            NOTE_SELECTION -> {
                title = getString(R.string.exercise_scale_dialog_choice_note_text)
                items = resources.getStringArray(R.array.list_notes).toMutableList()
            }
            SCALE_SELECTION -> {
                title = getString(R.string.exercise_scale_dialog_choice_mode_text)
                items = resources.getStringArray(R.array.list_scales).toMutableList()
            }
            else -> {
                items = ArrayList()
            }
        }

        materialDialogComponent.showSingleChoiceDialog(
            title,
            items,
            mSelectedItem,
            R.color.colorPrimary,
            true,
            object : SingleChoiceMaterialDialogListener {

                override fun onItemSelected(selectedItem: String) {
                    mSelectedItem = selectedItem
                    displaySelectedChoice(mSelectedItem, typeSelection)
                }

                override fun onCancelClick() {}

                override fun getPositionSelected(which: Int) {}
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