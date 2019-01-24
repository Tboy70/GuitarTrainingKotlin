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

    fun displayInputDialog(
        @StringRes titleRessourceId: Int,
        @StringRes hintRessourceId: Int,
        @StringRes positiveTextRessourceId: Int,
        onPositive: (String) -> Unit,
        oneLine: Boolean
    )

    fun displayInputDialogWithPrefilledMessage(titleRessourceId: Int, prefilledText: String, positiveTextRessourceId: Int, onPositive: (String) -> Unit, oneLine: Boolean)

    fun displayTextDialog(@StringRes title: Int? = null, content: String)
    fun displayTextDialog(@StringRes title: Int? = null, @StringRes content: Int)
    fun showTimerDialog(programActivity: ProgramActivity, durationLeft: Long, onTimerDialogDismiss: OnTimerDialogDismiss)
}