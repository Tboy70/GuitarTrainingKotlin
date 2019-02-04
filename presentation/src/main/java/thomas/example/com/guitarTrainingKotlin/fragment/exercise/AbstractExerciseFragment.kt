package thomas.example.com.guitarTrainingKotlin.fragment.exercise

import android.widget.TextView
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.ProgramActivity
import thomas.example.com.guitarTrainingKotlin.component.DurationComponent
import thomas.example.com.guitarTrainingKotlin.component.DialogComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.listener.DialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.component.listener.OnTimerDialogDismiss
import thomas.example.com.guitarTrainingKotlin.fragment.BaseExerciseFragment
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.DateTimeUtils
import javax.inject.Inject

abstract class AbstractExerciseFragment : BaseExerciseFragment() {

    @Inject
    lateinit var materialDialogComponentImpl: DialogComponentImpl

    @Inject
    lateinit var dialogComponent: DialogComponent

    @Inject
    lateinit var durationComponent: DurationComponent

    var rankExercise = ConstValues.CONST_ERROR
    var durationExercise = ConstValues.CONST_ERROR
    var durationLeft = DateTimeUtils.DEFAULT_DURATION_LEFT

    companion object {
        const val RANK_EXERCISE = "thomas.example.com.guitarTrainingKotlin.activity.RANK_EXERCISE"
        const val DURATION_EXERCISE = "thomas.example.com.guitarTrainingKotlin.activity.DURATION_EXERCISE"
    }

    fun setToolbar(toolbarTitle: Int) {
        if (activity is ProgramActivity) {
            (activity as ProgramActivity).setProgramToolbar(activity?.getString(toolbarTitle))
        }
    }

    fun setDurationUI(exerciseDurationTextView: TextView, exerciseDurationLeftTextView: TextView) {
        durationLeft = durationComponent.setDuration(
            durationExercise, durationLeft, exerciseDurationTextView,
            activity?.getString(R.string.generic_exercise_duration_text), exerciseDurationLeftTextView,
            activity?.getString(R.string.generic_exercise_duration_left_text)
        )
    }

    fun launchTimer(textViewToUpdate: TextView) {
        if (activity is ProgramActivity) {
            dialogComponent.showTimerDialog(durationLeft, object : OnTimerDialogDismiss {
                override fun onDismiss(timeCountInMilliseconds: Long) {
                    durationLeft = durationComponent.setDurationLeft(
                        textViewToUpdate,
                        getString(R.string.generic_exercise_duration_left_text),
                        timeCountInMilliseconds
                    )
                }
            })
        }
    }

    fun startNextExercise() {
        if (durationLeft.compareTo(0.0) != 0) {
//            dialogComponent.showMultiChoiceDialog(getString(R.string.confirm_next_exercise_title),
//                getString(R.string.confirm_next_exercise_content),
//                R.color.colorPrimary,
//                object : MultipleChoiceMaterialDialogListener {
//                    override fun onYesSelected() {
//                        if (activity is ProgramActivity && rankExercise != ConstValues.CONST_ERROR) {
//                            (activity as ProgramActivity).startExercise(rankExercise + 1)
//                        } else {
//                            activity?.finish()
//                        }
//                    }
//                })
        }
    }
}