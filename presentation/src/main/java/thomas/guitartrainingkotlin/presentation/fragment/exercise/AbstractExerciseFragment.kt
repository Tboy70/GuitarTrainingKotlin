package thomas.guitartrainingkotlin.presentation.fragment.exercise

import android.widget.TextView
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.ProgramActivity
import thomas.guitartrainingkotlin.presentation.component.DurationComponent
import thomas.guitartrainingkotlin.presentation.component.DialogComponentImpl
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.OnTimerDialogDismiss
import thomas.guitartrainingkotlin.presentation.fragment.BaseExerciseFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.DateTimeUtils
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
        const val RANK_EXERCISE = "thomas.example.com.guitarTrainingKotlin.baseActivity.RANK_EXERCISE"
        const val DURATION_EXERCISE = "thomas.example.com.guitarTrainingKotlin.baseActivity.DURATION_EXERCISE"
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
            dialogComponent.showTimerDialog(durationLeft, object :
                OnTimerDialogDismiss {
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
//                        if (baseActivity is ProgramActivity && rankExercise != ConstValues.CONST_ERROR) {
//                            (baseActivity as ProgramActivity).startExercise(rankExercise + 1)
//                        } else {
//                            baseActivity?.finish()
//                        }
//                    }
//                })
        }
    }
}