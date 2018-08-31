package thomas.example.com.guitarTrainingKotlin.component

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.listener.ExercisesUIComponentListener
import thomas.example.com.guitarTrainingKotlin.di.PerActivity

@PerActivity
class ExerciseUIComponent {

    private lateinit var horizontalLayoutContainingAllElements: LinearLayout
    private lateinit var verticalLayoutContainingTypeExerciseAndDurationExercise: LinearLayout

    private lateinit var buttonTypeExercise: Button
    private lateinit var durationExercise: EditText
    private lateinit var removeExerciseButton: ImageButton

    fun createNewExercise(activity: Activity, exercisesUIComponentListener: ExercisesUIComponentListener, textButton: String, textDuration: String, state: Int): LinearLayout {
        createLayout(activity)
        createUIViews(activity, textButton, textDuration, state)
        setListeners(durationExercise, buttonTypeExercise, removeExerciseButton, exercisesUIComponentListener, state)
        addViews(verticalLayoutContainingTypeExerciseAndDurationExercise, horizontalLayoutContainingAllElements)

        return horizontalLayoutContainingAllElements
    }

    private fun createLayout(activity: Activity) {
        horizontalLayoutContainingAllElements = LinearLayout(activity)
        horizontalLayoutContainingAllElements.orientation = LinearLayout.HORIZONTAL

        verticalLayoutContainingTypeExerciseAndDurationExercise = LinearLayout(activity)
        verticalLayoutContainingTypeExerciseAndDurationExercise.orientation = LinearLayout.VERTICAL
    }

    private fun createUIViews(activity: Activity, textButton: String, textDuration: String, state: Int) {
        buttonTypeExercise = Button(activity)
        buttonTypeExercise.setCompoundDrawables(null, null, ContextCompat.getDrawable(activity, R.drawable.ic_dropdown_black), null)
        buttonTypeExercise.text = activity.getString(R.string.fragment_user_programs_creation_type_exercise_text)
        durationExercise = EditText(activity)
        durationExercise.setHint(R.string.fragment_user_programs_creation_duration_exercise_text)


        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.weight = 1.0f
        params.gravity = Gravity.CENTER

        removeExerciseButton = ImageButton(activity)
        removeExerciseButton.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_delete))
        removeExerciseButton.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.transparent))
        removeExerciseButton.setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary))
        removeExerciseButton.layoutParams = params
    }

    private fun setListeners(durationExercise: EditText, buttonTypeExercise: Button, removeExerciseButton: ImageButton, exercisesUIComponentListener: ExercisesUIComponentListener, state: Int) {
        durationExercise.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                exercisesUIComponentListener.setDurationExerciseAction(durationExercise, buttonTypeExercise)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        buttonTypeExercise.setOnClickListener { exercisesUIComponentListener.setTypeExerciseButtonAction(buttonTypeExercise, durationExercise) }

        removeExerciseButton.setOnClickListener { v ->
            (v.parent.parent as ViewGroup).removeView(v.parent as ViewGroup)
        }
    }

    private fun addViews(verticalLayoutContainingTypeExerciseAndDurationExercise: LinearLayout, horizontalLayoutContainingAllElements: LinearLayout) {
        verticalLayoutContainingTypeExerciseAndDurationExercise.addView(buttonTypeExercise)
        verticalLayoutContainingTypeExerciseAndDurationExercise.addView(durationExercise)

        horizontalLayoutContainingAllElements.addView(verticalLayoutContainingTypeExerciseAndDurationExercise)

        horizontalLayoutContainingAllElements.addView(removeExerciseButton)
    }
}