package thomas.guitartrainingkotlin.presentation.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity

object ActivityExtensions {
    const val DISPLAY_UP = true
}

fun FragmentActivity.setSupportActionBar(toolbar: Toolbar, displayUp: Boolean = false) {
    if(this is AppCompatActivity) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayUp)
    }
}