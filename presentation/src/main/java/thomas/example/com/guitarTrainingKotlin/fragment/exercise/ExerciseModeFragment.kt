package thomas.example.com.guitarTrainingKotlin.fragment.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_exercise_mode.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.listener.SingleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.viewmodel.exercise.ExerciseModeViewModel
import javax.inject.Inject

class ExerciseModeFragment : AbstractExerciseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var exerciseModeViewModel: ExerciseModeViewModel

    private lateinit var items: List<String>

    private var mSelectedItem: String? = null

    companion object {
        const val NB_MODES = 7
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercise_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exerciseModeViewModel = ViewModelProviders.of(this, viewModelFactory).get(ExerciseModeViewModel::class.java)

        rankExercise = arguments?.getInt(AbstractExerciseFragment.RANK_EXERCISE) ?: ConstValues.CONST_ERROR
        durationExercise = arguments?.getInt(AbstractExerciseFragment.DURATION_EXERCISE) ?: ConstValues.CONST_ERROR

        handleClickModeButton()
        handleClickRandomButton()
        handleClickStartButton()
        handleClickNextButton()

        setDurationUI(fragment_exercise_mode_duration, fragment_exercise_mode_duration_left)
        setToolbar(R.string.toolbar_title_exercise_mode)

        exerciseModeViewModel.finishRandom.observeSafe(this) {
            val modesArray = this.resources.getStringArray(R.array.list_modes)
            mSelectedItem = modesArray[exerciseModeViewModel.modeValue]
            displaySelectedChoice(mSelectedItem)
        }
    }

    private fun handleClickModeButton() {
        fragment_exercise_mode_button_choice_mode.setOnClickListener {
            showSimpleChoiceDialog()
        }
    }

    private fun handleClickRandomButton() {
        fragment_exercise_mode_random_selection.setOnClickListener {
            exerciseModeViewModel.getRandomValue()
        }
    }

    private fun handleClickStartButton() {
        fragment_exercise_mode_button_start_exercise.setOnClickListener {
            launchTimer(fragment_exercise_mode_duration_left)
        }
    }

    private fun handleClickNextButton() {
        fragment_exercise_mode_next_button.setOnClickListener {
            startNextExercise()
        }
    }

    private fun showSimpleChoiceDialog() {
        val title = getString(R.string.exercise_mode_dialog_choice_mode_text)
        items = resources.getStringArray(R.array.list_modes).asList()

        materialDialogComponent.showSingleChoiceDialog(
            title,
            items,
            mSelectedItem,
            R.color.colorPrimary,
            true,
            object : SingleChoiceMaterialDialogListener {

                override fun onItemSelected(selectedItem: String) {
                    mSelectedItem = selectedItem
                    displaySelectedChoice(mSelectedItem)
                }

                override fun onCancelClick() {}

                override fun getPositionSelected(which: Int) {}
            })
    }

    private fun displaySelectedChoice(selectedItem: String?) {
        if (selectedItem != null && selectedItem.isNotEmpty()) {
            fragment_exercise_mode_button_choice_mode.text = selectedItem
        }
    }
}