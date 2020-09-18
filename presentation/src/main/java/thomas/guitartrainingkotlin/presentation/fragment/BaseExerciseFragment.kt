package thomas.guitartrainingkotlin.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.ProgramActivity
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.DurationComponent
import thomas.guitartrainingkotlin.presentation.component.listener.OnTimerDialogDismiss
import thomas.guitartrainingkotlin.presentation.fragment.ui.TimerDialogFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.DateTimeUtils
import javax.inject.Inject

private const val TIMER_DIALOG_FRAGMENT_TAG =
    "thomas.guitartrainingkotlin.presentation.fragment.exercise.AbstractExerciseFragment.TIMER_DIALOG_FRAGMENT_TAG"

abstract class BaseExerciseFragment : Fragment() {

    @Inject
    lateinit var dialogComponent: DialogComponent

    @Inject
    lateinit var durationComponent: DurationComponent

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    var rankExercise = ConstValues.CONST_ERROR
    var durationExercise = ConstValues.CONST_ERROR
    var durationLeft = DateTimeUtils.DEFAULT_DURATION_LEFT
    var nameProgram: String = ""

    companion object {
        const val NAME_PROGRAM = "thomas.example.com.guitarTrainingKotlin.baseActivity.NAME_PROGRAM"
        const val RANK_EXERCISE =
            "thomas.example.com.guitarTrainingKotlin.baseActivity.RANK_EXERCISE"
        const val DURATION_EXERCISE =
            "thomas.example.com.guitarTrainingKotlin.baseActivity.DURATION_EXERCISE"
    }

    fun setDurationUI(exerciseDurationTextView: TextView, exerciseDurationLeftTextView: TextView) {
        durationLeft = durationComponent.setDuration(
            durationExercise,
            durationLeft,
            exerciseDurationTextView,
            activity?.getString(R.string.generic_exercise_duration_text),
            exerciseDurationLeftTextView,
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
                    }).show(parentFragmentManager, TIMER_DIALOG_FRAGMENT_TAG)
            }
        }
    }

    fun startNextExercise() {
        if (durationLeft.compareTo(0.0) != 0) {
            dialogComponent.displayDualChoiceDialog(
                R.string.confirm_next_exercise_title,
                R.string.confirm_next_exercise_content,
                android.R.string.ok,
                android.R.string.cancel,
                onPositive = {
                    if (activity is ProgramActivity && rankExercise != ConstValues.CONST_ERROR) {
                        (activity as ProgramActivity).startExercise(rankExercise + 1, nameProgram)
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