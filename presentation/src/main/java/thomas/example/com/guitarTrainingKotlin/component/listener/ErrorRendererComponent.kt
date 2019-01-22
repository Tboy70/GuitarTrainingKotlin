package thomas.example.com.guitarTrainingKotlin.component.listener

import androidx.annotation.StringRes

interface ErrorRendererComponent {
    fun displayError(throwable: Throwable)
    fun displayErrorString(error: String)
    fun displayErrorString(@StringRes errorStringResource: Int)
    fun dismissSnackbar()
}