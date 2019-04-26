package thomas.guitartrainingkotlin.presentation.component

import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import thomas.guitartrainingkotlin.presentation.utils.GameUtils
import javax.inject.Inject

@PerActivity
class DialogComponentImpl @Inject constructor(
    private val baseActivity: BaseActivity
) : DialogComponent {

    private var materialDialog: MaterialDialog? = null

    override fun dismissDialog() {
        materialDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    override fun displaySingleChoiceDialog(title: Int, content: Int, positiveText: Int, onPositive: () -> Unit) {
        dismissDialog()
        materialDialog = MaterialDialog(baseActivity).show {
            title(title)
            message(content)
            positiveButton(res = positiveText) {
                onPositive()
            }
            negativeButton(android.R.string.cancel) {
                it.dismiss()
            }
        }

    }

    override fun displaySingleListChoiceDialog(
        title: Int,
        items: Int,
        positiveText: Int,
        onPositive: (text: String) -> Unit
    ) {
        dismissDialog()
        materialDialog = MaterialDialog(baseActivity).show {
            title(title)
            listItemsSingleChoice(items) { _, _, text ->
                onPositive(text)
            }
            positiveButton(res = positiveText)
        }
    }

    override fun displayDualChoiceDialog(
        title: Int, content: Int, positiveText: Int, negativeText: Int, onPositive: () -> Unit, onNegative: () -> Unit
    ) {
        dismissDialog()
        materialDialog = MaterialDialog(baseActivity).show {
            title(title)
            message(content)
            positiveButton(res = positiveText) {
                onPositive()
            }
            negativeButton(res = negativeText) {
                onNegative()
            }
        }
    }

    override fun displayCustomViewHelpScale(randomScale: String, positiveText: Int, onPositive: () -> Unit) {
        dismissDialog()
        materialDialog = MaterialDialog(baseActivity).show {}.apply {
            customView(R.layout.view_help_scale)
            cancelOnTouchOutside(true)
            positiveButton(positiveText) { onPositive() }
            val scaleInterval = GameUtils.retrieveScaleIntervalHelp(baseActivity, randomScale)
            getCustomView().findViewById<TextView>(R.id.view_help_scale_title).text =
                baseActivity.getString(R.string.dialog_view_help_scale_title, randomScale.toLowerCase())
            getCustomView().findViewById<TextView>(R.id.view_help_scale_interval).text = scaleInterval
        }
    }
}