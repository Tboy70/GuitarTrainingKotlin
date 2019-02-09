package thomas.guitartrainingkotlin.presentation.component.listener

import android.view.View

interface SnackbarComponent {

    fun displaySnackbar(
        view: View,
        content: String,
        length: Int,
        success : Boolean
    )
}