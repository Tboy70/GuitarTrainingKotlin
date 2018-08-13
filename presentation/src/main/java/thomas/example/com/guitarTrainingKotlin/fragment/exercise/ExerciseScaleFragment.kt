package thomas.example.com.guitarTrainingKotlin.fragment.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.ProgramActivity
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import kotlinx.android.synthetic.main.fragment_exercise_scale.*

class ExerciseScaleFragment : BaseFragment() {

    private var rankExercise: Int = ConstValues.CONST_ERROR
    private var durationExercise : Int = ConstValues.CONST_ERROR

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercise_scale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rankExercise = arguments?.getInt(ConstValues.RANK_EXERCISE) ?: ConstValues.CONST_ERROR
        durationExercise = arguments?.getInt(ConstValues.DURATION_EXERCISE) ?: ConstValues.CONST_ERROR

        fragment_exercise_scale_next_button.setOnClickListener {
            if (activity is ProgramActivity && rankExercise != ConstValues.CONST_ERROR) {
                (activity as ProgramActivity).startExercise(rankExercise + 1)
            } else {
                activity?.finish()
            }
        }
    }
}