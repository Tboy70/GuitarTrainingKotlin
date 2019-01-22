package thomas.example.com.guitarTrainingKotlin.component

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.component.listener.SnackbarComponent
import javax.inject.Inject

class SnackbarComponentImpl @Inject constructor(
    private val baseActivity: BaseActivity
) : SnackbarComponent {

    override fun displaySnackbar(view: View, content: String, length: Int, success: Boolean) {
        val snackbar = Snackbar.make(
            view,
            content,
            length
        )
        snackbar.view.setBackgroundColor(
            if (success) {
                ContextCompat.getColor(baseActivity, R.color.colorSuccess)
            } else {
                ContextCompat.getColor(baseActivity, R.color.colorFailed)
            }
        )
        snackbar.show()
    }

}