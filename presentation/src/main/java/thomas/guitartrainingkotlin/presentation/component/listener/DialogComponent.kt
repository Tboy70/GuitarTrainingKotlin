package thomas.guitartrainingkotlin.presentation.component.listener

import androidx.annotation.StringRes

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

    fun displaySingleListChoiceDialog(title: Int, items: Int, positiveText: Int, onPositive: (text: String) -> Unit)
}