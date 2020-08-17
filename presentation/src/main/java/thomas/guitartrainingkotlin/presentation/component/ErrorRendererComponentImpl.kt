package thomas.guitartrainingkotlin.presentation.component

import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.scopes.ActivityScoped
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.component.listener.ErrorRendererComponent
import thomas.guitartrainingkotlin.presentation.utils.ErrorUtils
import javax.inject.Inject

@ActivityScoped
class ErrorRendererComponentImpl @Inject constructor(
    private val baseActivity: BaseActivity
) : ErrorRendererComponent {

    private var snackbar: Snackbar? = null

    override fun displayError(throwable: Throwable) {
        snackbar = Snackbar.make(
            baseActivity.findViewById(android.R.id.content),
            ErrorUtils.translateException(baseActivity, throwable),
            Snackbar.LENGTH_LONG
        ).apply {
            setActionTextColor(Color.WHITE)
            view.setBackgroundColor(
                ContextCompat.getColor(
                    baseActivity,
                    android.R.color.holo_red_light
                )
            )

            val textView =
                view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView?.setTextColor(ContextCompat.getColor(baseActivity, android.R.color.white))
            textView?.maxLines = 3
            show()
        }
    }

    override fun displayErrorString(error: String) {
        snackbar = Snackbar.make(
            baseActivity.findViewById(android.R.id.content),
            error,
            Snackbar.LENGTH_LONG
        ).apply {
            setActionTextColor(Color.WHITE)
            view.setBackgroundColor(
                ContextCompat.getColor(
                    baseActivity,
                    android.R.color.holo_red_light
                )
            )

            val textView =
                view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView?.setTextColor(ContextCompat.getColor(baseActivity, android.R.color.white))
            textView?.maxLines = 3
            show()
        }
    }

    override fun displayErrorString(errorStringResource: Int) {
        snackbar = Snackbar.make(
            baseActivity.findViewById(android.R.id.content),
            errorStringResource,
            Snackbar.LENGTH_LONG
        ).apply {
            setActionTextColor(Color.WHITE)
            view.setBackgroundColor(
                ContextCompat.getColor(
                    baseActivity,
                    android.R.color.holo_red_light
                )
            )

            val textView =
                view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView?.setTextColor(ContextCompat.getColor(baseActivity, android.R.color.white))
            textView?.maxLines = 3
            show()
        }
    }

    override fun dismissSnackbar() {
        snackbar?.let { if (it.isShown) it.dismiss() }
    }
}