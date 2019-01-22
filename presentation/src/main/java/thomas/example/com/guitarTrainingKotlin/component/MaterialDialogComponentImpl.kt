package thomas.example.com.guitarTrainingKotlin.component

import com.afollestad.materialdialogs.MaterialDialog
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.component.listener.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.component.listener.SingleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerActivity
import javax.inject.Inject

@PerActivity
class MaterialDialogComponentImpl @Inject constructor(
    private val baseActivity: BaseActivity
) : MaterialDialogComponent {

    private lateinit var materialDialog: MaterialDialog

    override fun showProgressDialog(title: String, content: String, color: Int) {
        materialDialog = MaterialDialog.Builder(baseActivity)
            .title(title)
            .content(content)
            .progress(true, 0)
            .widgetColorRes(color)
            .canceledOnTouchOutside(false)
            .cancelable(false)
            .show()
    }

    override fun showSingleChoiceDialog(
            title: String, items: List<String>, selectedItem: String?, color: Int, cancelable: Boolean,
            singleChoiceMaterialDialogListener: SingleChoiceMaterialDialogListener
    ) {

        val selectedIndex = getSelectedItemIndex(items, selectedItem)
        materialDialog = MaterialDialog.Builder(baseActivity)
            .title(title)
            .items(items)
            .itemsCallbackSingleChoice(selectedIndex) { _, _, which, _ ->
                if (which >= 0) {
                    singleChoiceMaterialDialogListener.onItemSelected(items[which])
                    singleChoiceMaterialDialogListener.getPositionSelected(which)
                }
                true
            }
            .positiveText(android.R.string.ok)
            .positiveColorRes(color)
            .negativeText(android.R.string.cancel)
            .negativeColorRes(color)
            .onNegative { _, _ -> singleChoiceMaterialDialogListener.onCancelClick() }
            .widgetColorRes(color)
            .build()
        materialDialog.setCancelable(cancelable)
        materialDialog.show()
    }

    override fun showMultiChoiceDialog(
        title: String,
        content: String,
        color: Int,
        multipleChoiceMaterialDialogListener: MultipleChoiceMaterialDialogListener
    ) {
        materialDialog = MaterialDialog.Builder(baseActivity)
            .title(title)
            .content(content)
            .canceledOnTouchOutside(false)
            .positiveText(baseActivity.getString(android.R.string.yes))
            .positiveColorRes(color)
            .onPositive { _, _ -> multipleChoiceMaterialDialogListener.onYesSelected() }
            .negativeText(baseActivity.getString(android.R.string.no))
            .negativeColorRes(color)
            .onNegative { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun dismissDialog() {
        if (materialDialog != null) {
            materialDialog.dismiss()
        }
    }

    private fun getSelectedItemIndex(items: List<String>, selectedItem: String?): Int {
        return if (selectedItem != null && selectedItem.isNotEmpty()) {
            items.indexOf(selectedItem)
        } else -1
    }
}