package thomas.guitartrainingkotlin.presentation.component.listener

import android.widget.Button
import android.widget.LinearLayout

interface ExercisesUIComponent {

    fun createNewExercise(
        idExercise: String? = null,
        textButton: String? = null,
        textDuration: String? = null,
        onRemoveView: (idExercise: String?) -> Unit,
        onExerciseChosen: (button: Button) -> Unit
    ): LinearLayout
}