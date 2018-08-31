package thomas.example.com.guitarTrainingKotlin.fragment.user

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_user_program_details.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.ProgramActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.ProgramObjectWrapper
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.DateTimeUtils
import thomas.example.com.guitarTrainingKotlin.utils.ExerciseUtils
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserProgramDetailsViewModel
import thomas.example.com.model.Exercise
import javax.inject.Inject

class UserProgramDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userProgramDetailsViewModel: UserProgramDetailsViewModel

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    private lateinit var idProgram: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_user_program_details, container, false)

        userProgramDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserProgramDetailsViewModel::class.java)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireActivity().intent.extras
        if (bundle != null) {
            if (bundle.containsKey(ConstValues.ID_PROGRAM)) {
                idProgram = bundle.getString(ConstValues.ID_PROGRAM)
                checkProgramAndHideButtons(idProgram)
            }
        }

        userProgramDetailsViewModel.finishRetrieveProgram.observe(this, Observer<Boolean> {
            if (it == true) {
                val userProgramObjectWrapper = userProgramDetailsViewModel.userProgramObjectWrapper
                displayInformation(userProgramObjectWrapper)
            }
        })

        userProgramDetailsViewModel.finishLoading.observe(this, Observer<Boolean> {
            if (it != null) {
                materialDialogComponent.dismissDialog()
            }
        })

        fragment_user_program_details_start_button.setOnClickListener {
            startProgram()
        }
    }

    override fun onStart() {
        super.onStart()
        materialDialogComponent.showProgressDialog(getString(R.string.dialog_loading_program_details_title), getString(R.string.dialog_loading_content), R.color.colorPrimary)
        userProgramDetailsViewModel.getProgramById(idProgram)
    }

    private fun checkProgramAndHideButtons(idProgram: String?) {
        if (idProgram == ConstValues.DEFAULT_PROGRAM_THEORETICAL || idProgram == ConstValues.DEFAULT_PROGRAM_PRACTICAL) {
            fragment_user_program_details_remove_button.visibility = View.GONE
            fragment_user_program_details_update_button.visibility = View.GONE
            val constraintSet = ConstraintSet()
            constraintSet.clone(fragment_user_program_details_constraint_layout)
            constraintSet.clear(R.id.fragment_user_program_details_start_button, ConstraintSet.TOP)
            constraintSet.connect(R.id.fragment_user_program_details_start_button, ConstraintSet.BOTTOM, R.id.fragment_user_program_details_constraint_layout, ConstraintSet.BOTTOM, 24)
            constraintSet.applyTo(fragment_user_program_details_constraint_layout)
        }
    }

    @Suppress("DEPRECATION")
    private fun displayInformation(userProgramObjectWrapper: ProgramObjectWrapper) {

        fragment_user_program_details_name.text = userProgramObjectWrapper.program.nameProgram
        fragment_user_program_details_description.text = userProgramObjectWrapper.program.descriptionProgram

        val descriptionProgram = userProgramObjectWrapper.program.descriptionProgram
        if (descriptionProgram.isEmpty()) {
            fragment_user_program_details_description.text = activity?.getString(R.string.user_details_program_no_description)
        } else {
            fragment_user_program_details_description.text = descriptionProgram
        }

        val exercisesLinearLayout = LinearLayout(activity)
        exercisesLinearLayout.orientation = LinearLayout.VERTICAL

        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        exercisesLinearLayout.layoutParams = layoutParams

        for (exercise: Exercise in userProgramObjectWrapper.program.exercises) {
            val nameExercise = TextView(activity)
            nameExercise.text = ExerciseUtils.convertTypeExerciseToName(exercise.typeExercise, activity as Activity)
            nameExercise.setTextColor(ContextCompat.getColor(activity as Activity, android.R.color.black))
            nameExercise.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                    (activity as Activity).resources.getDimension(R.dimen.text_default) / (activity as Activity).resources.displayMetrics.density)

            val durationExercise = TextView(activity)
            if (Build.VERSION.SDK_INT < 23) {
                durationExercise.setTextAppearance(activity, R.style.TextAppearance_AppCompat_Caption)
            } else {
                durationExercise.setTextAppearance(R.style.TextAppearance_AppCompat_Caption)
            }
            if (exercise.durationExercise < DateTimeUtils.SECONDS_IN_ONE_MINUTE) {
                durationExercise.text = String.format(getString(R.string.user_details_duration_exercise_minutes_txt),
                        exercise.durationExercise.toString())
            } else {
                val hours = exercise.durationExercise / DateTimeUtils.SECONDS_IN_ONE_MINUTE
                val minutes = exercise.durationExercise % DateTimeUtils.SECONDS_IN_ONE_MINUTE

                durationExercise.text = String.format(getString(R.string.user_details_duration_exercise_hours_txt),
                        hours.toString(), minutes.toString())
            }

            durationExercise.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                    (activity as Activity).resources.getDimension(R.dimen.text_default) / (activity as Activity).resources.displayMetrics.density)

            exercisesLinearLayout.addView(nameExercise, 0)
            exercisesLinearLayout.addView(durationExercise, 1)
        }

        fragment_user_program_details_exercises.addView(exercisesLinearLayout, 1)

        if (activity is UserProgramActivity) {
            (activity as UserProgramActivity).setToolbar(userProgramObjectWrapper.program.nameProgram)
        }
    }

    private fun startProgram() {
        val intent = Intent(activity, ProgramActivity::class.java)
        intent.putExtra(ConstValues.ID_PROGRAM, idProgram)
        startActivity(intent)
        activity?.finish()
    }
}