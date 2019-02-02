package thomas.example.com.guitarTrainingKotlin.extension

import android.widget.CompoundButton

fun CompoundButton.setCustomChecked(checked: Boolean, listener: CompoundButton.OnCheckedChangeListener) {
    setOnCheckedChangeListener(null)
    isChecked = checked
    setOnCheckedChangeListener(listener)
}