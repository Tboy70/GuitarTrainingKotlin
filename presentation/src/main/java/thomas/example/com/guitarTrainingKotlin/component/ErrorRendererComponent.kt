package thomas.example.com.guitarTrainingKotlin.component

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.guitarTrainingKotlin.utils.ErrorUtils
import javax.inject.Inject

@PerActivity
class ErrorRendererComponent @Inject constructor(private val activity: BaseActivity) {

    companion object {
        const val ERROR_DISPLAY_MODE_SNACKBAR = 1
    }

    fun requestRenderError(it: Throwable, mode: Int, view: View) {
        if (mode == ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR) {
            displayErrorInFragmentView(it, activity, view)
        }
    }

    private fun displayErrorInFragmentView(throwable: Throwable, context: Context, view: View) {
        val snackBar = Snackbar.make(
            view,
            ErrorUtils.translateException(context.applicationContext, throwable),
            Snackbar.LENGTH_LONG
        )
        snackBar.setActionTextColor(Color.WHITE)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
        val yourSnackBarView = snackBar.view
        val textView = yourSnackBarView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.maxLines = 3
        snackBar.show()
    }

    fun displayErrorInFragmentView(error: String, context: Context, view: View?) {
        if (view != null) {
            val snackBar = Snackbar.make(view, error, Snackbar.LENGTH_LONG)
            snackBar.setActionTextColor(Color.WHITE)
            snackBar.view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
            val yourSnackBarView = snackBar.view
            val textView = yourSnackBarView.findViewById<View>(R.id.snackbar_text) as TextView
            textView.maxLines = 3
            snackBar.show()
        }
    }

    fun displayError(throwable: Throwable, activity: Activity, activityViewId: Int) {
        val snackBar = Snackbar.make(
            activity.findViewById(activityViewId),
            ErrorUtils.translateException(activity.applicationContext, throwable),
            Snackbar.LENGTH_LONG
        )
        snackBar.setActionTextColor(Color.WHITE)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.holo_red_light))
        val yourSnackBarView = snackBar.view
        val textView = yourSnackBarView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.maxLines = 3
        snackBar.show()
        throwable.printStackTrace()
    }

    fun displayErrorString(error: String, activity: Activity, activityViewId: Int) {
        val snackBar = Snackbar.make(activity.findViewById(activityViewId), error, Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(Color.WHITE)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.holo_red_light))
        val yourSnackBarView = snackBar.view
        val textView = yourSnackBarView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.maxLines = 3
        snackBar.show()
    }
}