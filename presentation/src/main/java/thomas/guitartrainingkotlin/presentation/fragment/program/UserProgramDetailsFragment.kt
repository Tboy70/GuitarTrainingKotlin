package thomas.guitartrainingkotlin.presentation.fragment.program

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_program_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.presentation.activity.ProgramActivity
import thomas.guitartrainingkotlin.presentation.component.DialogComponentImpl
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.DateTimeUtils
import thomas.guitartrainingkotlin.presentation.utils.ExerciseUtils
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.viewmodel.user.UserProgramDetailsViewModel
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class UserProgramDetailsFragment : Fragment(R.layout.fragment_user_program_details) {

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var dialogComponent: DialogComponentImpl

    private var navHost: View? = null


    private val viewModel by viewModels<UserProgramDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                activity?.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(true)
        activity?.setSupportActionBar(
            fragment_user_program_details_toolbar,
            ActivityExtensions.DISPLAY_UP
        )
    }

    private fun initiateView() {
        fragment_user_program_details_start_button.setOnClickListener {
            val intent = Intent(activity, ProgramActivity::class.java)
            intent.putExtra(ConstValues.ID_PROGRAM, viewModel.getIdProgram())
            startActivity(intent)
            activity?.finish()
        }

        fragment_user_program_details_update_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(ConstValues.ID_PROGRAM, viewModel.getIdProgram())
            navHost?.let { view ->
                Navigation.findNavController(view).navigate(R.id.user_program_update, bundle, null)
            }
        }

        fragment_user_program_details_remove_button.setOnClickListener {
            dialogComponent.displayDualChoiceDialog(
                R.string.dialog_remove_program_title,
                R.string.dialog_remove_program_confirm_content,
                android.R.string.yes,
                android.R.string.cancel,
                onPositive = {
                    viewModel.removeProgram()
                },
                onNegative = {
                    dialogComponent.dismissDialog()
                }
            )
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.programRetrievedLiveData.observeSafe(this) {
            displayInformation(it)
        }

        viewModel.viewState.observeSafe(this) {
            if (it.loading) {
                fragment_user_program_details_progress_bar.show()
            } else {
                fragment_user_program_details_progress_bar.gone()
            }
        }

        viewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }

        viewModel.finishProgramDeletion.observeSafe(this) {
            activity?.finish()
        }
    }

    private fun displayInformation(userProgramViewDataWrapper: ProgramViewDataWrapper) {

        ConstValues.DEFAULT_PROGRAM.contains(userProgramViewDataWrapper.getId()).let {
            if (it) {
                fragment_user_program_details_remove_button.gone()
                fragment_user_program_details_update_button.gone()

                val constraintSet = ConstraintSet()
                constraintSet.clone(fragment_user_program_details_constraint_layout)
                constraintSet.clear(
                    R.id.fragment_user_program_details_start_button,
                    ConstraintSet.TOP
                )
                constraintSet.connect(
                    R.id.fragment_user_program_details_start_button,
                    ConstraintSet.BOTTOM,
                    R.id.fragment_user_program_details_constraint_layout,
                    ConstraintSet.BOTTOM,
                    activity?.resources?.getInteger(R.integer.user_programs_details_start_to_bottom_margin)
                        ?: 0
                )
                constraintSet.applyTo(fragment_user_program_details_constraint_layout)
            } else {
                fragment_user_program_details_remove_button.show()
                fragment_user_program_details_update_button.show()
            }
        }

        val nameProgram = userProgramViewDataWrapper.getName()
        val descriptionProgram = userProgramViewDataWrapper.getDescription()

        fragment_user_program_details_name.text = nameProgram

        if (descriptionProgram.isEmpty()) {
            fragment_user_program_details_description.text =
                activity?.getString(R.string.user_details_program_no_description)
        } else {
            fragment_user_program_details_description.text = descriptionProgram
        }

        createExercisesList(userProgramViewDataWrapper.getExercises())
    }

    private fun createExercisesList(exercises: MutableList<Exercise>) {
        fragment_user_program_details_exercises.removeAllViewsInLayout()
        val exercisesLinearLayout = LinearLayout(activity)
        exercisesLinearLayout.orientation = LinearLayout.VERTICAL

        val layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        exercisesLinearLayout.layoutParams = layoutParams

        for (i in exercises.size - 1 downTo 0) {
            val nameExercise = TextView(activity)
            nameExercise.text = ExerciseUtils.convertTypeExerciseToName(
                exercises[i].typeExercise,
                activity as Activity
            )
            nameExercise.setTextColor(
                ContextCompat.getColor(
                    activity as Activity,
                    android.R.color.black
                )
            )
            nameExercise.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                (activity as Activity).resources.getDimension(R.dimen.text_big) / (activity as Activity).resources.displayMetrics.density
            )
            nameExercise.setTypeface(null, Typeface.BOLD)

            val durationExercise = TextView(activity)
            TextViewCompat.setTextAppearance(
                durationExercise,
                R.style.TextAppearance_AppCompat_Caption
            )

            if (exercises[i].durationExercise < DateTimeUtils.SECONDS_IN_ONE_MINUTE) {
                durationExercise.text = String.format(
                    getString(R.string.user_details_duration_exercise_minutes_txt),
                    exercises[i].durationExercise.toString()
                )
            } else {
                val hours = exercises[i].durationExercise / DateTimeUtils.SECONDS_IN_ONE_MINUTE
                val minutes = exercises[i].durationExercise % DateTimeUtils.SECONDS_IN_ONE_MINUTE

                durationExercise.text = String.format(
                    getString(R.string.user_details_duration_exercise_hours_txt),
                    hours.toString(),
                    minutes.toString()
                )
            }

            durationExercise.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                (activity as Activity).resources.getDimension(R.dimen.text_default) / (activity as Activity).resources.displayMetrics.density
            )

            exercisesLinearLayout.addView(nameExercise, 0)
            exercisesLinearLayout.addView(durationExercise, 1)
        }

        fragment_user_program_details_exercises.addView(exercisesLinearLayout, 0)
    }
}