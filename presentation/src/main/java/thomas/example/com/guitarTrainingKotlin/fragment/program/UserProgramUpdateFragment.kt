package thomas.example.com.guitarTrainingKotlin.fragment.program

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_user_program_update.*
import thomas.example.com.data.manager.SharedPrefsManagerImpl
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.ExerciseUIComponent
import thomas.example.com.guitarTrainingKotlin.component.DialogComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.listener.ExercisesUIComponentListener
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.component.listener.SingleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.ProgramViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.ExerciseUtils
import thomas.example.com.guitarTrainingKotlin.viewmodel.program.UserProgramUpdateViewModel
import thomas.example.com.model.Exercise
import javax.inject.Inject

class UserProgramUpdateFragment : BaseFragment<UserProgramUpdateViewModel>() {

    override val viewModelClass = UserProgramUpdateViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_program_update

    @Inject
    lateinit var exercisesUIComponent: ExerciseUIComponent

    @Inject
    lateinit var materialDialogComponentImpl: DialogComponentImpl

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    private lateinit var exercisesArray: Array<String>

    private var programViewDataWrapper: ProgramViewDataWrapper? = null

    private var selectedItem: String = ConstValues.EMPTY_STRING

    private var exercisesToBeRemoved: MutableList<Exercise> = ArrayList()

    companion object {
        const val PROGRAM_OBJECT_WRAPPER_KEY =
            "thomas.example.com.guitarTrainingKotlin.fragment.program.PROGRAM_OBJECT_WRAPPER_KEY"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exercisesArray = getInstrumentMode()

        val bundle = arguments
        if (bundle != null) {
            if (bundle.containsKey(PROGRAM_OBJECT_WRAPPER_KEY)) {
                programViewDataWrapper = bundle.getSerializable(PROGRAM_OBJECT_WRAPPER_KEY) as ProgramViewDataWrapper
            }
        }

        handleLiveData(view)
        initEditText()
        initExercisesList()
        handleClickValidateUpdateButton()
    }

    private fun handleLiveData(view: View) {
        viewModel.updateProgramSuccess.observeSafe(this) {
            materialDialogComponentImpl.dismissDialog()
            if (it != null && it == true) {
                activity?.finish()
            } else if (it != null && it == false) {
                if (viewModel.errorThrowable != null) {
//                    errorRendererComponent.requestRenderError(
//                        viewModel.errorThrowable as Throwable,
//                        ErrorRendererComponentImpl.ERROR_DISPLAY_MODE_SNACKBAR,
//                        view
//                    )
                }
            }
        }
    }

    private fun initEditText() {
        fragment_user_program_update_name.setText(programViewDataWrapper?.getName())
        fragment_user_program_update_description.setText(programViewDataWrapper?.getDescription())
    }

    private fun initExercisesList() {
        for (exercise in programViewDataWrapper?.getExercises().orEmpty()) {
            val horizontalLayoutContainingAllElements = exercisesUIComponent.createNewExercise(
                object : ExercisesUIComponentListener {

                    override fun setTypeExerciseButtonAction(buttonTypeExercise: Button, durationExercise: EditText) {
                        val title = getString(R.string.generic_exercise_choice_creation_program)
                        val items = exercisesArray.toList()

//                        materialDialogComponentImpl.showSingleChoiceDialog(
//                            title,
//                            items,
//                            selectedItem,
//                            R.color.colorPrimary,
//                            true,
//                            object : SingleChoiceMaterialDialogListener {
//                                override fun onItemSelected(selectedItem: String) {
//                                    this@UserProgramUpdateFragment.selectedItem = selectedItem
//                                    buttonTypeExercise.text = selectedItem
//                                }
//
//                                override fun getPositionSelected(which: Int) {
//                                }
//
//                                override fun onCancelClick() {
//                                }
//
//                            })
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
                SharedPrefsManagerImpl.CURRENT_INSTRUMENT_MODE,
                SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR
            ) == SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR
        ) {
            resources.getStringArray(R.array.list_exercises_guitar)
        } else {
            resources.getStringArray(R.array.list_exercises_bass)
        }
    }

    private fun handleClickValidateUpdateButton() {
        fragment_user_program_update_validate_button.setOnClickListener {

//            materialDialogComponentImpl.showMultiChoiceDialog(
//                getString(R.string.dialog_update_program_title),
//                getString(R.string.dialog_update_program_confirm_content),
//                R.color.colorPrimary,
//                object : MultipleChoiceMaterialDialogListener {
//                    override fun onYesSelected() {
//                        materialDialogComponentImpl.showProgressDialog(
//                            getString(R.string.dialog_update_program_title),
//                            getString(R.string.dialog_update_program_content),
//                            R.color.colorPrimary
//                        )
//
//                        for (i in 0 until fragment_user_program_update_exercises_list.childCount) {
//
//                            val exerciseName =
//                                (((fragment_user_program_update_exercises_list.getChildAt(i) as LinearLayout).getChildAt(
//                                    0
//                                ) as LinearLayout).getChildAt(0) as Button).text.toString()
//                            val exerciseDurationValue =
//                                (((fragment_user_program_update_exercises_list.getChildAt(i) as LinearLayout).getChildAt(
//                                    0
//                                ) as LinearLayout).getChildAt(1) as EditText).text.toString()
//
//                            programViewDataWrapper?.getExercises()?.get(i)?.typeExercise =
//                                    ExerciseUtils.getTypeExerciseIdByName(exerciseName, activity as UserProgramActivity)
//                            programViewDataWrapper?.getExercises()?.get(i)?.durationExercise = exerciseDurationValue.toInt()
//                        }
//
//                        programViewDataWrapper?.getExercises()?.removeAll(exercisesToBeRemoved)
//
//                        // TODO : Check that ! Too many ? and !! (?)
//                        if (programViewDataWrapper != null) {
//                            viewModel.checkInformationAndValidateUpdate(
//                                    programViewDataWrapper!!.getId(),
//                                fragment_user_program_update_name.text.toString(),
//                                fragment_user_program_update_description.text.toString(),
//                                    programViewDataWrapper!!.getExercises(),
//                                exercisesToBeRemoved
//                            )
//                        }
//                    }
//                })
        }
    }
}