package thomas.example.com.guitarTrainingKotlin.component

import android.app.Activity
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.listener.OnTimerDialogDismiss
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.guitarTrainingKotlin.fragment.ui.TimerDialogFragment
import thomas.example.com.utils.ConstantTags
import javax.inject.Inject

@PerActivity
class DialogComponent @Inject constructor() {

    fun showTimerDialog(activity: Activity, durationExercise: Long, onTimerDialogDismiss: OnTimerDialogDismiss) {
        val timerDialogFragment = TimerDialogFragment.newInstance(activity.getString(R.string.timer_title), durationExercise)
        timerDialogFragment.setTimerDialogDismissListener(onTimerDialogDismiss)
        timerDialogFragment.show(activity.fragmentManager, ConstantTags.DIALOG.tag)
    }
}