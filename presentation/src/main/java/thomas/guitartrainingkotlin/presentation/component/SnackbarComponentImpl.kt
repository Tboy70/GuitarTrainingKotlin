package thomas.guitartrainingkotlin.presentation.component

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.scopes.ActivityScoped
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.component.listener.SnackbarComponent
import javax.inject.Inject

@ActivityScoped
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