package thomas.guitartrainingkotlin.presentation.fragment.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_exercise_speed.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.utils.ConstValues

class ExerciseSpeedFragment : AbstractExerciseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercise_speed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rankExercise = arguments?.getInt(RANK_EXERCISE) ?: ConstValues.CONST_ERROR
        durationExercise = arguments?.getInt(DURATION_EXERCISE) ?: ConstValues.CONST_ERROR

        handleClickStartButton()
        handleClickNextButton()

        setDurationUI(fragment_exercise_speed_duration, fragment_exercise_speed_duration_left)
        setToolbar(R.string.toolbar_title_exercise_speed)
    }

    private fun handleClickStartButton() {
        fragment_exercise_speed_button_start_exercise.setOnClickListener {
            launchTimer(fragment_exercise_speed_duration_left)
        }
    }

    private fun handleClickNextButton() {
        fragment_exercise_speed_next_button.setOnClickListener {
            startNextExercise()
        }
    }
}