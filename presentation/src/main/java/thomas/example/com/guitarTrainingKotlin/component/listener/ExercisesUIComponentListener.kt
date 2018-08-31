package thomas.example.com.guitarTrainingKotlin.component.listener

import android.widget.Button
import android.widget.EditText

interface ExercisesUIComponentListener {

    fun setTypeExerciseButtonAction(buttonTypeExercise: Button, durationExercise: EditText)

    fun setDurationExerciseAction(durationExercise: EditText, buttonTypeExercise: Button)
}