package thomas.example.com.guitarTrainingKotlin.component

import android.app.Activity
import com.afollestad.materialdialogs.MaterialDialog
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import javax.inject.Inject

@PerActivity
class MaterialDialogComponent @Inject constructor(private val activity: BaseActivity) {

    private lateinit var materialDialog: MaterialDialog

    fun showProgressDialog(activity: Activity, title: String, content: String, color: Int) {
        materialDialog = MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .progress(true, 0)
                .widgetColorRes(color)
                .canceledOnTouchOutside(false)
                .show()
    }

    fun dismissDialog() {
        materialDialog.dismiss()
    }

}