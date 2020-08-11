package thomas.guitartrainingkotlin.presentation.fragment.program

import android.os.Bundle
import android.util.SparseArray
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_program_creation.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.ErrorRendererComponent
import thomas.guitartrainingkotlin.presentation.component.listener.ExercisesUIComponent
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.utils.ExerciseUtils
import thomas.guitartrainingkotlin.presentation.view.custom.CustomExerciseView
import thomas.guitartrainingkotlin.presentation.viewmodel.program.UserProgramCreationViewModel
import javax.inject.Inject

@AndroidEntryPoint
class UserProgramCreationFragment : Fragment(R.layout.fragment_user_program_creation) {

    @Inject
    lateinit var exercisesUIComponent: ExercisesUIComponent

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var dialogComponent: DialogComponent

    private var exercisesArray: Int = 0

    private val viewModel by viewModels<UserProgramCreationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateToolbar()
        initiateView()
        initiateViewModelObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(true)
        activity?.setSupportActionBar(fragment_user_program_creation_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateView() {
        fragment_user_program_creation_add_exercise.setOnClickListener {
            enableCreationAddExerciseButton(false)
            addFieldToCreateExercise()
        }

        fragment_user_program_creation_validation.setOnClickListener {

            val exercises = SparseArray<String>()

            fragment_user_program_creation_exercises.children.forEach { child ->
                val key = (((child as CustomExerciseView).getChildAt(0) as ConstraintLayout)
                    .findViewById<Button>(R.id.view_custom_exercise_type).text.toString())
                val value = ((child.getChildAt(0) as ConstraintLayout)
                    .findViewById<EditText>(R.id.view_custom_exercise_duration).text.toString())

                activity?.let { activity ->
                    exercises.append(ExerciseUtils.getTypeExerciseIdByName(key, activity), value)
                }
            }

            dialogComponent.displayDualChoiceDialog(
                R.string.dialog_create_program_title,
                R.string.dialog_create_program_content,
                android.R.string.yes,
                android.R.string.no,
                onPositive = {
                    viewModel.checkInformationAndValidateCreation(
                        fragment_user_program_creation_name.text.toString(),
                        fragment_user_program_creation_description.text.toString(),
                        exercises
                    )
                },
                onNegative = {}
            )
        }
    }

    private fun initiateViewModelObservers() {

        viewModel.retrievedInstrumentMode.observeSafe(this) {
            exercisesArray = if (it == InstrumentModeValues.INSTRUMENT_MODE_GUITAR) {
                R.array.list_exercises_guitar
            } else {
                R.array.list_exercises_bass
            }
        }

        viewModel.informationNotRightLiveEvent.observeSafe(this) {
            if (it) {
                context?.let { context ->
                    errorRendererComponent.displayErrorString(context.getString(R.string.error_wrong_info))
                }
            }
        }
        viewModel.createdProgramLiveEvent.observeSafe(this) {
            activity?.finish()
        }

        viewModel.viewState.observeSafe(this) {
            if (it.loading) {
                fragment_user_program_creation_progress_bar.show()
            } else {
                fragment_user_program_creation_progress_bar.hide()
            }
        }

        viewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }
    }


    private fun addFieldToCreateExercise() {
        exercisesUIComponent.createNewExercise(
            fragment_user_program_creation_exercises,
            onRemoveView = {
                enableCreationAddExerciseButton(true)
            },
            onExerciseChosen = { buttonTypeExercise ->
                dialogComponent.displaySingleListChoiceDialog(
                    R.string.generic_exercise_choice_creation_program,
                    exercisesArray,
                    android.R.string.ok,
                    onPositive = { exerciseTitle ->
                        buttonTypeExercise.text = exerciseTitle
                        enableCreationAddExerciseButton(true)
                    })
            }
        )
    }

    private fun enableCreationAddExerciseButton(enableButton: Boolean) {
        fragment_user_program_creation_add_exercise.isEnabled = enableButton
    }
}