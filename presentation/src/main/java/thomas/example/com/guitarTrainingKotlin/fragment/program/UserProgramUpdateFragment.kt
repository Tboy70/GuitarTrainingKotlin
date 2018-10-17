package thomas.example.com.guitarTrainingKotlin.fragment.program

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_user_program_update.*
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.ExerciseUIComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.ExercisesUIComponentListener
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.component.listener.SingleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.ProgramObjectWrapper
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.ExerciseUtils
import thomas.example.com.guitarTrainingKotlin.viewmodel.program.UserProgramUpdateViewModel
import thomas.example.com.model.Exercise
import javax.inject.Inject

class UserProgramUpdateFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userProgramUpdateViewModel: UserProgramUpdateViewModel

    @Inject
    lateinit var exercisesUIComponent: ExerciseUIComponent

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    private lateinit var exercisesArray: Array<String>

    private var programObjectWrapper: ProgramObjectWrapper? = null

    private var selectedItem: String = ConstValues.EMPTY_STRING

    private var exercisesToBeRemoved: MutableList<Exercise> = ArrayList()

    companion object {
        const val PROGRAM_OBJECT_WRAPPER_KEY =
            "thomas.example.com.guitarTrainingKotlin.fragment.program.PROGRAM_OBJECT_WRAPPER_KEY"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_program_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userProgramUpdateViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(UserProgramUpdateViewModel::class.java)

        exercisesArray = getInstrumentMode()

        val bundle = arguments
        if (bundle != null) {
            if (bundle.containsKey(PROGRAM_OBJECT_WRAPPER_KEY)) {
                programObjectWrapper = bundle.getSerializable(PROGRAM_OBJECT_WRAPPER_KEY) as ProgramObjectWrapper
            }
        }

        handleLiveData(view)
        initEditText()
        initExercisesList()
        handleClickValidateUpdateButton()
    }

    private fun handleLiveData(view: View) {
        userProgramUpdateViewModel.updateProgramSuccess.observeSafe(this) {
            materialDialogComponent.dismissDialog()
            if (it != null && it == true) {
                activity?.finish()
            } else if (it != null && it == false) {
                if (userProgramUpdateViewModel.errorThrowable != null) {
                    errorRendererComponent.requestRenderError(
                        userProgramUpdateViewModel.errorThrowable as Throwable,
                        ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR,
                        view
                    )
                }
            }
        }
    }

    private fun initEditText() {
        fragment_user_program_update_name.setText(programObjectWrapper?.program?.nameProgram)
        fragment_user_program_update_description.setText(programObjectWrapper?.program?.descriptionProgram)
    }

    private fun initExercisesList() {
        for (exercise in programObjectWrapper?.program?.exercises.orEmpty()) {
            val horizontalLayoutContainingAllElements = exercisesUIComponent.createNewExercise(
                object : ExercisesUIComponentListener {

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
                                    this@UserProgramUpdateFragment.selectedItem = selectedItem
                                    buttonTypeExercise.text = selectedItem
                                }

                                override fun getPositionSelected(which: Int) {
                                }

                                override fun onCancelClick() {
                                }

                            })
                    }

                    override fun onRemoveView() {
                        exercisesToBeRemoved.add(exercise)
                    }

                },
                ExerciseUtils.convertTypeExerciseToName(exercise.typeExercise, activity as UserProgramActivity),
                exercise.durationExercise.toString(),
                ExerciseUIComponent.UPDATE_STATE
            )

            fragment_user_program_update_exercises_list.addView(horizontalLayoutContainingAllElements)
        }
    }

    private fun getInstrumentMode(): Array<String> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return if (prefs.getString(
                ModuleSharedPrefsImpl.CURRENT_INSTRUMENT_MODE,
                ModuleSharedPrefsImpl.INSTRUMENT_MODE_GUITAR
            ) == ModuleSharedPrefsImpl.INSTRUMENT_MODE_GUITAR
        ) {
            resources.getStringArray(R.array.list_exercises_guitar)
        } else {
            resources.getStringArray(R.array.list_exercises_bass)
        }
    }

    private fun handleClickValidateUpdateButton() {
        fragment_user_program_update_validate_button.setOnClickListener {

            materialDialogComponent.showMultiChoiceDialog(
                getString(R.string.dialog_update_program_title),
                getString(R.string.dialog_update_program_confirm_content),
                R.color.colorPrimary,
                object : MultipleChoiceMaterialDialogListener {
                    override fun onYesSelected() {
                        materialDialogComponent.showProgressDialog(
                            getString(R.string.dialog_update_program_title),
                            getString(R.string.dialog_update_program_content),
                            R.color.colorPrimary
                        )

                        val currentProgram = programObjectWrapper?.program

                        for (i in 0 until fragment_user_program_update_exercises_list.childCount) {

                            val exerciseName =
                                (((fragment_user_program_update_exercises_list.getChildAt(i) as LinearLayout).getChildAt(
                                    0
                                ) as LinearLayout).getChildAt(0) as Button).text.toString()
                            val exerciseDurationValue =
                                (((fragment_user_program_update_exercises_list.getChildAt(i) as LinearLayout).getChildAt(
                                    0
                                ) as LinearLayout).getChildAt(1) as EditText).text.toString()

                            currentProgram?.exercises?.get(i)?.typeExercise =
                                    ExerciseUtils.getTypeExerciseIdByName(exerciseName, activity as UserProgramActivity)
                            currentProgram?.exercises?.get(i)?.durationExercise = exerciseDurationValue.toInt()
                        }

                        currentProgram?.exercises?.removeAll(exercisesToBeRemoved)

                        if (currentProgram != null) {
                            userProgramUpdateViewModel.checkInformationAndValidateUpdate(
                                currentProgram.idProgram,
                                fragment_user_program_update_name.text.toString(),
                                fragment_user_program_update_description.text.toString(),
                                currentProgram.exercises,
                                exercisesToBeRemoved
                            )
                        }
                    }
                })
        }
    }
}