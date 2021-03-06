package thomas.guitartrainingkotlin.presentation.extension

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

inline fun textChangedListener(crossinline textChanged: () -> Unit) = object : TextWatcher {
    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        textChanged()
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun EditText.getInput() = text.toString()

fun EditText.isNotEmpty() = text.isNotEmpty()