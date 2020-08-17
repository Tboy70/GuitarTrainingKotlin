package thomas.guitartrainingkotlin.presentation.component.listener

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import thomas.guitartrainingkotlin.presentation.view.custom.CustomExerciseView

interface ExercisesUIComponent {

    fun createNewExercise(
        rootLayout: LinearLayout,
        textButton: String? = null,
        textDuration: String? = null,
        onRemoveView: (customView : View) -> Unit,
        onExerciseChosen: (button: Button) -> Unit
    )
}