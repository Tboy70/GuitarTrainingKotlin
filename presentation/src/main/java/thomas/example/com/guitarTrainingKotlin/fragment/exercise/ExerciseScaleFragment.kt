package thomas.example.com.guitarTrainingKotlin.fragment.exercise

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_exercise_scale.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.ProgramActivity
import thomas.example.com.guitarTrainingKotlin.component.DialogComponent
import thomas.example.com.guitarTrainingKotlin.component.DurationComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.OnTimerDialogDismiss
import thomas.example.com.guitarTrainingKotlin.component.listener.SingleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.DateTimeUtils
import thomas.example.com.guitarTrainingKotlin.viewmodel.ExerciseScaleViewModel
import javax.inject.Inject

class ExerciseScaleFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var exerciseScaleViewModel: ExerciseScaleViewModel

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    @Inject
    lateinit var dialogComponent: DialogComponent

    @Inject
    lateinit var durationComponent: DurationComponent

    private lateinit var items: List<String>
    private var mSelectedItem: String? = null
    private var rankExercise = ConstValues.CONST_ERROR
    private var durationExercise = ConstValues.CONST_ERROR
    private var durationLeft = DateTimeUtils.DEFAULT_DURATION_LEFT

    companion object {
        const val SCALE_NOTE_SELECTION = 1
        const val SCALE_MODE_SELECTION = 2
        const val NB_NOTES = 12
        const val NB_MODES = 3
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercise_scale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exerciseScaleViewModel = ViewModelProviders.of(this, viewModelFactory).get(ExerciseScaleViewModel::class.java)

        rankExercise = arguments?.getInt(ConstValues.RANK_EXERCISE) ?: ConstValues.CONST_ERROR
        durationExercise = arguments?.getInt(ConstValues.DURATION_EXERCISE) ?: ConstValues.CONST_ERROR

        handleClickNoteButton()
        handleClickModeButton()
        handleClickRandomButton()
        handleClickStartButton()
        handleClickNextButton()

        setDurationUI()
        setToolbar(activity?.getString(R.string.toolbar_title_exercise_scale))

        exerciseScaleViewModel.finishRandom.observe(this, Observer<Boolean> {
            val notesArray = this.resources.getStringArray(R.array.list_notes)
            mSelectedItem = notesArray[exerciseScaleViewModel.scaleNoteValue]
            displaySelectedChoice(mSelectedItem, SCALE_NOTE_SELECTION)

            val modesArray = this.resources.getStringArray(R.array.list_tones)
            mSelectedItem = modesArray[exerciseScaleViewModel.scaleToneValue]
            displaySelectedChoice(mSelectedItem, SCALE_MODE_SELECTION)
        })
    }

    private fun handleClickNoteButton() {
        fragment_exercise_scale_button_choice_note.setOnClickListener {
            showSimpleChoiceDialog(SCALE_NOTE_SELECTION)
        }
    }

    private fun handleClickModeButton() {
        fragment_exercise_scale_button_choice_mode.setOnClickListener {
            showSimpleChoiceDialog(SCALE_MODE_SELECTION)
        }
    }

    private fun handleClickRandomButton() {
        fragment_exercise_scale_random_selection.setOnClickListener {
            exerciseScaleViewModel.getRandomValue()
        }
    }

    private fun handleClickStartButton() {
        fragment_exercise_scale_button_start_exercise.setOnClickListener {
            if (activity is ProgramActivity) {
                dialogComponent.showTimerDialog(activity as ProgramActivity, durationLeft, object : OnTimerDialogDismiss {
                    override fun onDismiss(timeCountInMilliseconds: Long) {
                        durationLeft = durationComponent.setDurationLeft(
                                fragment_exercise_scale_duration_left,
                                getString(R.string.generic_exercise_duration_left_text),
                                timeCountInMilliseconds)
                    }
                })
            }
        }
    }

    private fun handleClickNextButton() {
        fragment_exercise_scale_next_button.setOnClickListener {
            if (activity is ProgramActivity && rankExercise != ConstValues.CONST_ERROR) {
                (activity as ProgramActivity).startExercise(rankExercise + 1)
            } else {
                activity?.finish()
            }
        }
    }

    private fun showSimpleChoiceDialog(typeSelection: Int) {
        var title: String = ConstValues.EMPTY_STRING
        when (typeSelection) {
            SCALE_NOTE_SELECTION -> {
                title = getString(R.string.exercise_scale_dialog_choice_note_text)
                items = resources.getStringArray(R.array.list_notes).asList()
            }
            SCALE_MODE_SELECTION -> {
                title = getString(R.string.exercise_scale_dialog_choice_mode_text)
                items = resources.getStringArray(R.array.list_tones).asList()
            }
            else -> {
                items = ArrayList()
            }
        }

        materialDialogComponent.showSingleChoiceDialog(title, items, mSelectedItem, R.color.colorPrimary, true, object : SingleChoiceMaterialDialogListener {

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
                SCALE_NOTE_SELECTION -> fragment_exercise_scale_button_choice_note.text = selectedItem
                SCALE_MODE_SELECTION -> fragment_exercise_scale_button_choice_mode.text = selectedItem
            }
        }
    }

    private fun setDurationUI() {
        durationLeft = durationComponent.setDuration(durationExercise, durationLeft, fragment_exercise_scale_duration,
                activity?.getString(R.string.generic_exercise_duration_text), fragment_exercise_scale_duration_left,
                activity?.getString(R.string.generic_exercise_duration_left_text)
        )
    }

    private fun setToolbar(toolbarTitle: String?) {
        if (activity is ProgramActivity) {
            (activity as ProgramActivity).setProgramToolbar(toolbarTitle)
        }
    }
}