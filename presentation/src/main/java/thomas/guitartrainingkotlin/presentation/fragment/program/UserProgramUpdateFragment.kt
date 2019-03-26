package thomas.guitartrainingkotlin.presentation.fragment.program

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_user_program_update.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.data.manager.sharedprefs.SharedPrefsManagerImpl
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.presentation.activity.UserProgramActivity
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.ErrorRendererComponent
import thomas.guitartrainingkotlin.presentation.component.listener.ExercisesUIComponent
import thomas.guitartrainingkotlin.presentation.extension.ActivityExtensions
import thomas.guitartrainingkotlin.presentation.extension.getInput
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.extension.setSupportActionBar
import thomas.guitartrainingkotlin.presentation.fragment.BaseFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.ExerciseUtils
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.viewmodel.program.UserProgramUpdateViewModel
import javax.inject.Inject

class UserProgramUpdateFragment : BaseFragment<UserProgramUpdateViewModel>() {

    override val viewModelClass = UserProgramUpdateViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_program_update

    @Inject
    lateinit var exercisesUIComponent: ExercisesUIComponent

    @Inject
    lateinit var dialogComponent: DialogComponent

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    private var exercisesArray: Int? = null

    private var programViewDataWrapper: ProgramViewDataWrapper? = null

    private var exercisesToBeRemoved: MutableList<Exercise> = ArrayList()
    private var exercisesIdToBeRemoved: MutableList<String> = ArrayList()

    private var navHost: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.retrieveInstrumentMode()

        activity?.let {
            it.intent.extras?.let { bundle ->
                bundle.getString(ConstValues.ID_PROGRAM)?.let { idProgram ->
                    viewModel.setIdProgram(idProgram)
                }
            }
            navHost = it.findViewById(R.id.user_program_nav_host_fragment) as View
        }

        viewModel.getProgramById()


        initiateToolbar()
        initiateView()
        initiateViewModelObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navHost?.let { view ->
                    Navigation.findNavController(view).navigateUp()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(true)
        activity?.setSupportActionBar(fragment_user_program_update_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateView() {
        fragment_user_program_update_validate_button.setOnClickListener {

            dialogComponent.displayDualChoiceDialog(
                R.string.dialog_update_program_title,
                R.string.dialog_update_program_confirm_content,
                android.R.string.yes,
                android.R.string.cancel,
                onPositive = {
                    for (i in 0 until fragment_user_program_update_exercises_list.childCount) {
                        val exerciseName =
                            (((fragment_user_program_update_exercises_list.getChildAt(i) as LinearLayout)
                                .getChildAt(0) as LinearLayout)
                                .getChildAt(0) as Button)
                                .text.toString()
                        val exerciseDurationValue =
                            (((fragment_user_program_update_exercises_list.getChildAt(i) as LinearLayout)
                                .getChildAt(0) as LinearLayout)
                                .getChildAt(1) as EditText)
                                .text.toString()

                        programViewDataWrapper?.getExercises()?.get(i)?.typeExercise =
                            ExerciseUtils.getTypeExerciseIdByName(exerciseName, activity as UserProgramActivity)
                        programViewDataWrapper?.getExercises()?.get(i)?.durationExercise = exerciseDurationValue.toInt()
                    }

                    programViewDataWrapper?.getExercises()?.removeAll(exercisesToBeRemoved)

                    programViewDataWrapper?.let { programViewDataWrapper ->
                        viewModel.checkInformationAndValidateUpdate(
                            programViewDataWrapper.getId(),
                            fragment_user_program_update_name.getInput(),
                            fragment_user_program_update_description.getInput(),
                            programViewDataWrapper.getExercises(),
                            exercisesToBeRemoved
                        )
                    }
                },
                onNegative = {
                    dialogComponent.dismissDialog()
                }
            )

        }
    }

    private fun initiateViewModelObservers() {

        viewModel.instrumentModeRetrievedLiveEvent.observeSafe(this) {
            exercisesArray =
                if (it == SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR) { // TODO : Where take this value ?
                    R.array.list_exercises_guitar
                } else {
                    R.array.list_exercises_bass
                }
        }

        viewModel.programRetrievedLiveData.observeSafe(this) {
            programViewDataWrapper = it
            fragment_user_program_update_name.setText(it.getName())
            fragment_user_program_update_description.setText(it.getDescription())
            initExercisesList(it.getExercises())
        }

        viewModel.updateProgramSuccess.observeSafe(this) {
            activity?.finish()
        }
    }

    private fun initExercisesList(exercises: MutableList<Exercise>) {
        activity?.let { activity ->
            exercises.forEach {
                val horizontalLayoutContainingAllElements = exercisesUIComponent.createNewExercise(
                    it.idExercise,
                    ExerciseUtils.convertTypeExerciseToName(it.typeExercise, activity),
                    it.durationExercise.toString(),
                    onRemoveView = { idExerciseToRemove ->
                        idExerciseToRemove?.let { id ->
                            exercisesIdToBeRemoved.add(id)
                        }
                    },
                    onExerciseChosen = { buttonTypeExercise ->
                        exercisesArray?.let { exercisesArray ->
                            dialogComponent.displaySingleListChoiceDialog(
                                R.string.generic_exercise_choice_creation_program,
                                exercisesArray,
                                android.R.string.ok,
                                onPositive = { exerciseTitle ->
                                    buttonTypeExercise.text = exerciseTitle
                                })
                        }
                    })
                fragment_user_program_update_exercises_list.addView(horizontalLayoutContainingAllElements)
            }
        }
    }
}