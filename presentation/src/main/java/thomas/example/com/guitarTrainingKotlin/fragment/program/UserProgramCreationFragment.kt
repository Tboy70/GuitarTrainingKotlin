package thomas.example.com.guitarTrainingKotlin.fragment.program

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.SparseArray
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_user_program_creation.*
import thomas.example.com.data.manager.SharedPrefsManagerImpl
import thomas.example.com.data.utils.InstrumentModeUtils
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.ExerciseUIComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.ExercisesUIComponentListener
import thomas.example.com.guitarTrainingKotlin.component.listener.SingleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.ExerciseUtils
import thomas.example.com.guitarTrainingKotlin.viewmodel.program.UserProgramCreationViewModel
import javax.inject.Inject

class UserProgramCreationFragment : BaseFragment<UserProgramCreationViewModel>() {

    override val viewModelClass = UserProgramCreationViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_program_creation

    @Inject
    lateinit var exercisesUIComponent: ExerciseUIComponent

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    private var selectedItem: String = ConstValues.EMPTY_STRING

    private lateinit var exercisesArray: Array<String>

    companion object {
        const val FULL_ALPHA = 1.0f
        const val HALF_ALPHA = 0.5f
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exercisesArray = getStringArrayGivenInstrumentMode()

        handleLiveData(view)
        handleClickAddExercise()
        handleClickCreateProgram()
    }

    private fun handleLiveData(view: View) {

        viewModel.creationProgramSuccess.observeSafe(this) {
            fragmentManager?.popBackStack()
        }

        viewModel.viewState.observeSafe(this) {
            if (it.displayingLoading) {
                materialDialogComponent.showProgressDialog(
                        getString(R.string.dialog_login_title),
                        getString(R.string.dialog_login_content),
                        R.color.colorPrimary
                )
            } else {
                materialDialogComponent.dismissDialog()
            }
        }

        viewModel.errorEvent.observeSafe(this) {
            val errorTriggered = viewModel.errorThrowable
            if (it.ERROR_TRIGGERED && errorTriggered != null) {
//                errorRendererComponent.requestRenderError(
//                        viewModel.errorThrowable as Throwable,
//                        ErrorRendererComponentImpl.ERROR_DISPLAY_MODE_SNACKBAR,
//                        view
//                )
            }
        }
    }

    private fun handleClickAddExercise() {
        fragment_user_program_creation_add_exercise.setOnClickListener {
            addFieldToCreateExercise()
            enableCreationAddExerciseButton(false)
        }
    }

    private fun handleClickCreateProgram() {
        fragment_user_program_creation_validation.setOnClickListener {
            // TODO : Check if it's not too long
            val exercises = SparseArray<String>()
            for (i in 0 until fragment_user_program_creation_exercises.childCount) {
                val key =
                    (((fragment_user_program_creation_exercises.getChildAt(i) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(
                            0
                    ) as Button).text.toString()
                val value =
                    (((fragment_user_program_creation_exercises.getChildAt(i) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(
                            1
                    ) as EditText).text.toString()

                exercises.append(ExerciseUtils.getTypeExerciseIdByName(key, activity as UserPanelActivity), value)
            }

            val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val instrumentMode = InstrumentModeUtils.getIntValueFromInstrumentMode(
                    prefs.getString(
                            SharedPrefsManagerImpl.CURRENT_INSTRUMENT_MODE,
                            SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR
                    )
            ).toString()

            viewModel.checkInformationAndValidateCreation(
                    fragment_user_program_creation_name.text.toString(),
                    fragment_user_program_creation_description.text.toString(),
                    exercises,
                    instrumentMode
            )
        }
    }

    private fun addFieldToCreateExercise() {
        val horizontalLayoutContainingAllElements =
            exercisesUIComponent.createNewExercise(object : ExercisesUIComponentListener {

                override fun setTypeExerciseButtonAction(buttonTypeExercise: Button, durationExercise: EditText) {
                    val title = getString(R.string.generic_exercise_choice_creation_program)
                    val items = exercisesArray.toList()

                    materialDialogComponent.showSingleChoiceDialog(
                            title,
                            items,
                            selectedItem,
                            R.color.colorPrimary,
                            true,
                            object : SingleChoiceMaterialDialogListener {
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

    private fun getStringArrayGivenInstrumentMode(): Array<String> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return if (prefs.getString(
                    SharedPrefsManagerImpl.CURRENT_INSTRUMENT_MODE,
                    SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR
            ) == SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR
        ) {
            resources.getStringArray(R.array.list_exercises_guitar)
        } else {
            resources.getStringArray(R.array.list_exercises_bass)
        }
    }

    private fun enableCreationAddExerciseButton(enableButton: Boolean) {
        if (enableButton) {
            fragment_user_program_creation_add_exercise.isEnabled = true
            fragment_user_program_creation_add_exercise.setBackgroundColor(
                    ContextCompat.getColor(
                            activity as UserPanelActivity,
                            R.color.colorPrimary
                    )
            )
            fragment_user_program_creation_add_exercise.alpha = FULL_ALPHA
        } else {
            fragment_user_program_creation_add_exercise.isEnabled = false
            fragment_user_program_creation_add_exercise.setBackgroundColor(
                    ContextCompat.getColor(
                            activity as UserPanelActivity,
                            R.color.colorGrey
                    )
            )
            fragment_user_program_creation_add_exercise.alpha = HALF_ALPHA
        }
    }
}