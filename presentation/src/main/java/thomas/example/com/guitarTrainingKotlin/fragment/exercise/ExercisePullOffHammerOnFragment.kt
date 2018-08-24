package thomas.example.com.guitarTrainingKotlin.fragment.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_exercise_palm_mute.*
import kotlinx.android.synthetic.main.fragment_exercise_pull_off_hammer_on.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues

class ExercisePullOffHammerOnFragment : AbstractExerciseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercise_pull_off_hammer_on, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rankExercise = arguments?.getInt(AbstractExerciseFragment.RANK_EXERCISE) ?: ConstValues.CONST_ERROR
        durationExercise = arguments?.getInt(AbstractExerciseFragment.DURATION_EXERCISE) ?: ConstValues.CONST_ERROR

        handleClickStartButton()
        handleClickNextButton()

        setDurationUI(fragment_exercise_pull_off_hammer_on_duration, fragment_exercise_pull_off_hammer_on_duration_left)
        setToolbar(R.string.toolbar_title_exercise_pull_off_hammer_on)
    }

    private fun handleClickStartButton() {
        fragment_exercise_pull_off_hammer_on_button_start_exercise.setOnClickListener {
            launchTimer(fragment_exercise_pull_off_hammer_on_duration_left)
        }
    }

    private fun handleClickNextButton() {
        fragment_exercise_pull_off_hammer_on_next_button.setOnClickListener {
            startNextExercise()
        }
    }
}