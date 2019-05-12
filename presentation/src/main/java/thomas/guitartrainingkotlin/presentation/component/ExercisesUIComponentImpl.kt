package thomas.guitartrainingkotlin.presentation.component

import android.widget.Button
import android.widget.LinearLayout
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.component.listener.ExercisesUIComponent
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import thomas.guitartrainingkotlin.presentation.view.custom.CustomExerciseView
import javax.inject.Inject

@PerActivity
class ExercisesUIComponentImpl @Inject constructor(val baseActivity: BaseActivity) : ExercisesUIComponent {

    private lateinit var rootLayout: LinearLayout
    private lateinit var customExerciseView: CustomExerciseView

    override fun createNewExercise(
        rootLayout: LinearLayout,
        idExercise: String?,
        textButton: String?,
        textDuration: String?,
        onRemoveView: (idExercise: String?) -> Unit,
        onExerciseChosen: (button: Button) -> Unit
    ) {
        this.rootLayout = rootLayout

        customExerciseView = CustomExerciseView(baseActivity)
        rootLayout.addView(customExerciseView)

        customExerciseView.setListeners(onRemoveView, onExerciseChosen, idExercise)
        customExerciseView.setValue(textDuration, textButton)
    }
}