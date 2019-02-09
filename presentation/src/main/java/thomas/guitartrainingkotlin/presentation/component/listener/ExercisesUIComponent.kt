package thomas.guitartrainingkotlin.presentation.component.listener

import android.widget.Button
import android.widget.LinearLayout

interface ExercisesUIComponent {

    fun createNewExercise(
        textButton: String? = null,
        textDuration: String? = null,
        onRemoveView: () -> Unit,
        onExerciseChosen: (button: Button) -> Unit
    ): LinearLayout
}