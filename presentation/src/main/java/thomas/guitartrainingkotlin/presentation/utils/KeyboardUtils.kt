package thomas.guitartrainingkotlin.presentation.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        activity.currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}