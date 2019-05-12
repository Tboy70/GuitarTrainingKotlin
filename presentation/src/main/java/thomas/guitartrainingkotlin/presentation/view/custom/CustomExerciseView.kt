package thomas.guitartrainingkotlin.presentation.view.custom

import android.content.Context
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_custom_exercise.view.*
import thomas.guitartrainingkotlin.R

class CustomExerciseView(context: Context) : ConstraintLayout(context) {

    init {
        View.inflate(context, R.layout.view_custom_exercise, this)
    }

    fun setValue(textDuration: String?, textButton: String?) {
        view_custom_exercise_duration.inputType = InputType.TYPE_CLASS_NUMBER
        textDuration?.let {
            view_custom_exercise_duration.setText(textDuration)
        } ?: view_custom_exercise_duration.setHint(R.string.user_program_creation_duration_exercise_hint)
        textButton?.let {
            view_custom_exercise_type.text = it
        } ?: view_custom_exercise_type.setText(R.string.user_program_creation_type_exercise_text)
    }

    fun setListeners(
        onRemoveView: (idExercise: String?) -> Unit,
        onExerciseChosen: (button: Button) -> Unit,
        idExercise: String?
    ) {
        view_custom_exercise_delete_icon.setOnClickListener { v ->
            (v.parent.parent as ViewGroup).removeView(v.parent as ViewGroup)
            onRemoveView(idExercise)
        }

        view_custom_exercise_type.setOnClickListener {
            onExerciseChosen(view_custom_exercise_type)
        }
    }
}