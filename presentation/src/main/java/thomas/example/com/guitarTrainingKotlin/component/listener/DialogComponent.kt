package thomas.example.com.guitarTrainingKotlin.component.listener

import androidx.annotation.StringRes
import thomas.example.com.guitarTrainingKotlin.activity.ProgramActivity

interface DialogComponent {
    fun dismissDialog()

    fun displaySingleChoiceDialog(
        @StringRes title: Int,
        @StringRes content: Int,
        @StringRes positiveText: Int,
        onPositive: () -> Unit
    )

    fun displayDualChoiceDialog(
        @StringRes title: Int,
        @StringRes content: Int,
        @StringRes positiveText: Int,
        @StringRes negativeText: Int,
        onPositive: () -> Unit,
        onNegative: () -> Unit
    )

    fun showTimerDialog(durationLeft: Long, onTimerDialogDismiss: OnTimerDialogDismiss)
}