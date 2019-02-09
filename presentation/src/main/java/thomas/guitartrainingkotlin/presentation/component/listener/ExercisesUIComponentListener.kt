package thomas.guitartrainingkotlin.presentation.component.listener

import android.widget.Button
import android.widget.EditText

interface ExercisesUIComponentListener {

    fun setTypeExerciseButtonAction(buttonTypeExercise: Button, durationExercise: EditText)

    fun onRemoveView()
}