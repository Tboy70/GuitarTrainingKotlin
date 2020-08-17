package thomas.guitartrainingkotlin.presentation.component

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import dagger.hilt.android.scopes.ActivityScoped
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.component.listener.ExercisesUIComponent
import thomas.guitartrainingkotlin.presentation.view.custom.CustomExerciseView
import javax.inject.Inject

@ActivityScoped
class ExercisesUIComponentImpl @Inject constructor(val baseActivity: BaseActivity) :
    ExercisesUIComponent {

    private lateinit var rootLayout: LinearLayout
    private lateinit var customExerciseView: CustomExerciseView

    override fun createNewExercise(
        rootLayout: LinearLayout,
        textButton: String?,
        textDuration: String?,
        onRemoveView: (customView : View) -> Unit,
        onExerciseChosen: (button: Button) -> Unit
    ) {
        this.rootLayout = rootLayout

        customExerciseView = CustomExerciseView(baseActivity)
        rootLayout.addView(customExerciseView)

        customExerciseView.setListeners(onRemoveView, onExerciseChosen)
        customExerciseView.setValue(textDuration, textButton)
    }
}