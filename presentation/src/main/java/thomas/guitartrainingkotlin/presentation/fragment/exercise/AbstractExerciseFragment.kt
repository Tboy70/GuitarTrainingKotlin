package thomas.guitartrainingkotlin.presentation.fragment.exercise

import android.widget.TextView
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.ProgramActivity
import thomas.guitartrainingkotlin.presentation.component.DurationComponent
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.OnTimerDialogDismiss
import thomas.guitartrainingkotlin.presentation.fragment.BaseExerciseFragment
import thomas.guitartrainingkotlin.presentation.fragment.ui.TimerDialogFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.DateTimeUtils
import javax.inject.Inject

private const val TIMER_DIALOG_FRAGMENT_TAG =
    "thomas.guitartrainingkotlin.presentation.fragment.exercise.AbstractExerciseFragment.TIMER_DIALOG_FRAGMENT_TAG"

abstract class AbstractExerciseFragment : BaseExerciseFragment() {

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

    fun setDurationUI(exerciseDurationTextView: TextView, exerciseDurationLeftTextView: TextView) {
        durationLeft = durationComponent.setDuration(
            durationExercise, durationLeft, exerciseDurationTextView,
            activity?.getString(R.string.generic_exercise_duration_text), exerciseDurationLeftTextView,
            activity?.getString(R.string.generic_exercise_duration_left_text)
        )
    }

    fun launchTimer(textViewToUpdate: TextView) {
        activity?.let { activity ->
            if (activity is ProgramActivity) {
                var toBeDisplayedInTimer = durationLeft
                if (durationLeft.compareTo(0.0) == 0) {
                    toBeDisplayedInTimer =
                        durationExercise * DateTimeUtils.SECONDS_IN_ONE_MINUTE * DateTimeUtils.MINUTE_TO_MILLISECONDS
                }
                TimerDialogFragment.newInstance(
                    activity.getString(R.string.timer_title),
                    toBeDisplayedInTimer,
                    object : OnTimerDialogDismiss {
                        override fun onDismiss(timeCountInMilliseconds: Long) {
                            durationLeft = durationComponent.setDurationLeft(
                                textViewToUpdate,
                                getString(R.string.generic_exercise_duration_left_text),
                                timeCountInMilliseconds
                            )
                        }
                    }).show(fragmentManager, TIMER_DIALOG_FRAGMENT_TAG)
            }
        }
    }

    fun startNextExercise() {
        if (durationLeft.compareTo(0.0) != 0) {
            dialogComponent.displayDualChoiceDialog(
                R.string.confirm_next_exercise_title,
                R.string.confirm_next_exercise_content,
                android.R.string.yes,
                android.R.string.no,
                onPositive = {
                    if (activity is ProgramActivity && rankExercise != ConstValues.CONST_ERROR) {
                        (activity as ProgramActivity).startExercise(rankExercise + 1)
                    } else {
                        activity?.finish()
                    }
                },
                onNegative = {
                    dialogComponent.dismissDialog()
                })
        }
    }
}