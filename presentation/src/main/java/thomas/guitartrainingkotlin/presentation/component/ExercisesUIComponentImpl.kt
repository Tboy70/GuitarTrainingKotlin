package thomas.guitartrainingkotlin.presentation.component

import android.text.InputType
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.BaseActivity
import thomas.guitartrainingkotlin.presentation.component.listener.ExercisesUIComponent
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import javax.inject.Inject

@PerActivity
class ExercisesUIComponentImpl @Inject constructor(
    val baseActivity: BaseActivity
) : ExercisesUIComponent {

    private lateinit var buttonTypeExercise: Button
    private lateinit var durationExercise: EditText
    private lateinit var removeExerciseButton: ImageButton
    private lateinit var horizontalLayoutContainingAllElements: LinearLayout
    private lateinit var verticalLayoutContainingTypeExerciseAndDurationExercise: LinearLayout

    override fun createNewExercise(
        textButton: String?, textDuration: String?,
        onRemoveView: () -> Unit,
        onExerciseChosen: (button: Button) -> Unit
    ): LinearLayout {
        createLayout()
        createUIViews(textButton, textDuration)
        setListeners(onRemoveView, onExerciseChosen)
        addViews(verticalLayoutContainingTypeExerciseAndDurationExercise, horizontalLayoutContainingAllElements)
        return horizontalLayoutContainingAllElements
    }

    private fun setListeners(onRemoveView: () -> Unit, onExerciseChosen: (button: Button) -> Unit) {
        removeExerciseButton.setOnClickListener { v ->
            (v.parent.parent as ViewGroup).removeView(v.parent as ViewGroup)
            onRemoveView()
        }

        buttonTypeExercise.setOnClickListener {
            onExerciseChosen(buttonTypeExercise)
        }
    }

    private fun createLayout() {
        horizontalLayoutContainingAllElements = LinearLayout(baseActivity)
        horizontalLayoutContainingAllElements.orientation = LinearLayout.HORIZONTAL
        verticalLayoutContainingTypeExerciseAndDurationExercise = LinearLayout(baseActivity)
        verticalLayoutContainingTypeExerciseAndDurationExercise.orientation = LinearLayout.VERTICAL
    }

    private fun createUIViews(textButton: String?, textDuration: String?) {
        buttonTypeExercise = Button(baseActivity)
        buttonTypeExercise.setCompoundDrawables(
            null,
            null,
            ContextCompat.getDrawable(baseActivity, R.drawable.ic_dropdown_black),
            null
        )

        buttonTypeExercise.text = textButton?.let { it }
            ?: baseActivity.getString(R.string.user_program_creation_type_exercise_text)

        durationExercise = EditText(baseActivity)
        durationExercise.inputType = InputType.TYPE_CLASS_NUMBER
        textDuration?.let {
            durationExercise.setText(textDuration)
        } ?: durationExercise.setHint(R.string.user_program_creation_duration_exercise_hint)


        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.CENTER

        removeExerciseButton = ImageButton(baseActivity)
        removeExerciseButton.setImageDrawable(ContextCompat.getDrawable(baseActivity, R.drawable.ic_delete))
        removeExerciseButton.setBackgroundColor(ContextCompat.getColor(baseActivity, android.R.color.transparent))
        removeExerciseButton.setColorFilter(ContextCompat.getColor(baseActivity, R.color.colorPrimary))
        removeExerciseButton.layoutParams = params
    }

    private fun addViews(
        verticalLayoutContainingTypeExerciseAndDurationExercise: LinearLayout,
        horizontalLayoutContainingAllElements: LinearLayout
    ) {
        verticalLayoutContainingTypeExerciseAndDurationExercise.addView(buttonTypeExercise)
        verticalLayoutContainingTypeExerciseAndDurationExercise.addView(durationExercise)

        horizontalLayoutContainingAllElements.addView(verticalLayoutContainingTypeExerciseAndDurationExercise)

        horizontalLayoutContainingAllElements.addView(removeExerciseButton)
    }
}