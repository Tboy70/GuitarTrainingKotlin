package thomas.example.com.guitarTrainingKotlin.fragment.program

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_user_program_creation.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.ExerciseUIComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.ExercisesUIComponentListener
import thomas.example.com.guitarTrainingKotlin.component.listener.SingleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.ExerciseUtils
import thomas.example.com.guitarTrainingKotlin.viewmodel.program.UserProgramCreationViewModel
import javax.inject.Inject

class UserProgramCreationFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userProgramCreationViewModel: UserProgramCreationViewModel

    @Inject
    lateinit var exercisesUIComponent: ExerciseUIComponent

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    private var selectedItem: String = ConstValues.EMPTY_STRING

    companion object {
        const val FULL_ALPHA = 1.0f
        const val HALF_ALPHA = 0.5f
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_program_creation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userProgramCreationViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserProgramCreationViewModel::class.java)

        userProgramCreationViewModel.creationProgramSuccess.observe(this, Observer<Boolean> {
            materialDialogComponent.dismissDialog()
            if (it != null && it == true) {
                fragmentManager?.popBackStack()
            }
        })

        userProgramCreationViewModel.creationProgramFailure.observe(this, Observer<Boolean> {
            materialDialogComponent.dismissDialog()
            if (it != null && it == true) {
                if (userProgramCreationViewModel.errorThrowable != null) {
                    errorRendererComponent.requestRenderError(userProgramCreationViewModel.errorThrowable as Throwable, ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR, view)
                }
            }
        })

        handleClickAddExercise()
        handleClickCreateProgram()
    }

    private fun handleClickCreateProgram() {
        fragment_user_program_creation_validation.setOnClickListener {
            materialDialogComponent.showProgressDialog(getString(R.string.dialog_loading_program_creation_title), getString(R.string.dialog_loading_program_creation_content), R.color.colorPrimary)
            val exercises = SparseArray<String>()
            for (i in 0 until fragment_user_program_creation_exercises.childCount) {
                val key = (((fragment_user_program_creation_exercises.getChildAt(i) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(0) as Button).text.toString()
                val value = (((fragment_user_program_creation_exercises.getChildAt(i) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(1) as EditText).text.toString()

                exercises.append(ExerciseUtils.getTypeExerciseIdByName(key, activity as UserPanelActivity), value)
            }
            userProgramCreationViewModel.checkInformationAndValidateCreation(fragment_user_program_creation_name.text.toString(), fragment_user_program_creation_description.text.toString(), exercises)
        }
    }

    private fun handleClickAddExercise() {
        fragment_user_program_creation_add_exercise.setOnClickListener {
            addFieldToCreateExercise()
            enableCreationAddExerciseButton(false)
        }
    }

    private fun enableCreationAddExerciseButton(enableButton: Boolean) {
        if (enableButton) {
            fragment_user_program_creation_add_exercise.isEnabled = true
            fragment_user_program_creation_add_exercise.setBackgroundColor(ContextCompat.getColor(activity as UserPanelActivity, R.color.colorPrimary))
            fragment_user_program_creation_add_exercise.alpha = FULL_ALPHA
        } else {
            fragment_user_program_creation_add_exercise.isEnabled = false
            fragment_user_program_creation_add_exercise.setBackgroundColor(ContextCompat.getColor(activity as UserPanelActivity, R.color.colorGrey))
            fragment_user_program_creation_add_exercise.alpha = HALF_ALPHA
        }
    }

    private fun addFieldToCreateExercise() {
        val horizontalLayoutContainingAllElements = exercisesUIComponent.createNewExercise(object : ExercisesUIComponentListener {

            override fun setTypeExerciseButtonAction(buttonTypeExercise: Button, durationExercise: EditText) {
                val exercisesArray = resources.getStringArray(R.array.list_exercises)
                val title = getString(R.string.generic_exercise_choice_creation_program)
                val items = exercisesArray.toList()

                materialDialogComponent.showSingleChoiceDialog(title, items, selectedItem, R.color.colorPrimary, true, object : SingleChoiceMaterialDialogListener {
                    override fun onItemSelected(selectedItem: String) {
                        this@UserProgramCreationFragment.selectedItem = selectedItem
                        buttonTypeExercise.text = selectedItem
                        enableCreationAddExerciseButton(true)
                    }

                    override fun getPositionSelected(which: Int) {
                    }

                    override fun onCancelClick() {
                    }
                })
            }

            override fun onRemoveView() {
                enableCreationAddExerciseButton(true)
            }
        }, ConstValues.EMPTY_STRING, ConstValues.EMPTY_STRING, ExerciseUIComponent.CREATE_STATE)

        fragment_user_program_creation_exercises.addView(horizontalLayoutContainingAllElements)
    }
}