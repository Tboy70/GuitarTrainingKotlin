package thomas.example.com.guitarTrainingKotlin.fragment.program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_user_program_creation.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.component.ExerciseUIComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.ExercisesUIComponentListener
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import javax.inject.Inject

class UserProgramCreationFragment : BaseFragment() {

    @Inject
    lateinit var exercisesUIComponent: ExerciseUIComponent

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_program_creation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleClickAddExercise()
    }

    private fun handleClickAddExercise() {
        fragment_user_program_creation_add_exercise.setOnClickListener {
            addFieldToCreateExercise()
        }
    }

    // TODO : Check if we can directly inject activity in exerciseUIComponent
    private fun addFieldToCreateExercise() {
        val horizontalLayoutContainingAllElements = exercisesUIComponent.createNewExercise(activity as UserProgramActivity, object : ExercisesUIComponentListener {
            override fun setTypeExerciseButtonAction(buttonTypeExercise: Button, durationExercise: EditText) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun setDurationExerciseAction(durationExercise: EditText, buttonTypeExercise: Button) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }, null, null, 1)
    }
}