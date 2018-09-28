package thomas.example.com.guitarTrainingKotlin.fragment.program

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v4.content.ContextCompat
import android.support.v4.widget.TextViewCompat
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_user_program_details.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.ProgramActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
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

        handleLiveData()
        handleStartProgram()
        handleUpdateProgram()
        handleRemoveProgram()
    }

    override fun onStart() {
        super.onStart()
        materialDialogComponent.showProgressDialog(getString(R.string.dialog_details_program_title), getString(R.string.dialog_details_program_content), R.color.colorPrimary)
        userProgramDetailsViewModel.getProgramById(idProgram)
    }

    private fun checkProgramAndHideButtons(idProgram: String?) {
        if (idProgram == ConstValues.DEFAULT_PROGRAM_THEORETICAL_GUITAR || idProgram == ConstValues.DEFAULT_PROGRAM_PRACTICAL_GUITAR
                || idProgram == ConstValues.DEFAULT_PROGRAM_THEORETICAL_BASS || idProgram == ConstValues.DEFAULT_PROGRAM_PRACTICAL_BASS) {
            fragment_user_program_details_remove_button.visibility = View.GONE
            fragment_user_program_details_update_button.visibility = View.GONE
            val constraintSet = ConstraintSet()
            constraintSet.clone(fragment_user_program_details_constraint_layout)
            constraintSet.clear(R.id.fragment_user_program_details_start_button, ConstraintSet.TOP)
            constraintSet.connect(R.id.fragment_user_program_details_start_button, ConstraintSet.BOTTOM, R.id.fragment_user_program_details_constraint_layout, ConstraintSet.BOTTOM, 24)
            constraintSet.applyTo(fragment_user_program_details_constraint_layout)
        }
    }

    private fun handleLiveData() {
        userProgramDetailsViewModel.finishRetrieveProgramForDetails.observe(this, Observer<Boolean> {
            if (it == true) {
                val userProgramObjectWrapper = userProgramDetailsViewModel.userProgramObjectWrapper
                displayInformation(userProgramObjectWrapper)
                userProgramDetailsViewModel.finishRetrieveProgramForDetails.removeObservers(this)
            }
        })

        userProgramDetailsViewModel.finishLoading.observe(this, Observer<Boolean> {
            if (it != null) {
                materialDialogComponent.dismissDialog()
            }
        })

        userProgramDetailsViewModel.finishProgramDeletion.observe(this, Observer<Boolean> {
            if (it != null && it == true) {
                activity?.finish()
            }
        })
    }

    private fun handleStartProgram() {
        fragment_user_program_details_start_button.setOnClickListener {
            val intent = Intent(activity, ProgramActivity::class.java)
            intent.putExtra(ConstValues.ID_PROGRAM, idProgram)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun handleUpdateProgram() {
        fragment_user_program_details_update_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(UserProgramUpdateFragment.PROGRAM_OBJECT_WRAPPER_KEY, userProgramDetailsViewModel.userProgramObjectWrapper)

            val host = activity?.supportFragmentManager?.findFragmentById(R.id.user_program_nav_host_fragment) as NavHostFragment
            NavHostFragment.findNavController(host).navigate(R.id.user_program_update, bundle, null)
        }
    }

    private fun handleRemoveProgram() {
        fragment_user_program_details_remove_button.setOnClickListener {
            materialDialogComponent.showMultiChoiceDialog(getString(R.string.dialog_remove_program_title), getString(R.string.dialog_remove_program_confirm_content), R.color.colorPrimary, object : MultipleChoiceMaterialDialogListener {
                override fun onYesSelected() {
                    materialDialogComponent.showProgressDialog(getString(R.string.dialog_remove_program_title), getString(R.string.dialog_remove_program_content), R.color.colorPrimary)
                    userProgramDetailsViewModel.removeProgram(idProgram)
                }
            })
        }
    }

    private fun displayInformation(userProgramObjectWrapper: ProgramObjectWrapper) {

        val nameProgram = userProgramObjectWrapper.program.nameProgram
        val descriptionProgram = userProgramObjectWrapper.program.descriptionProgram

        setToolbar(nameProgram)

        fragment_user_program_details_name.text = nameProgram

        if (descriptionProgram.isEmpty()) {
            fragment_user_program_details_description.text = activity?.getString(R.string.user_details_program_no_description)
        } else {
            fragment_user_program_details_description.text = descriptionProgram
        }

        createExercisesList(userProgramObjectWrapper.program.exercises)
    }

    private fun setToolbar(nameProgram: String) {
        if (activity is UserProgramActivity) {
            (activity as UserProgramActivity).setToolbar(nameProgram)
        }
    }

    private fun createExercisesList(exercises: MutableList<Exercise>) {
        val exercisesLinearLayout = LinearLayout(activity)
        exercisesLinearLayout.orientation = LinearLayout.VERTICAL

        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        exercisesLinearLayout.layoutParams = layoutParams

        for (i in exercises.size - 1 downTo 0) {
            val nameExercise = TextView(activity)
            nameExercise.text = ExerciseUtils.convertTypeExerciseToName(exercises[i].typeExercise, activity as Activity)
            nameExercise.setTextColor(ContextCompat.getColor(activity as Activity, android.R.color.black))
            nameExercise.setTextSize(TypedValue.COMPLEX_UNIT_SP, (activity as Activity).resources.getDimension(R.dimen.text_default) / (activity as Activity).resources.displayMetrics.density)

            val durationExercise = TextView(activity)
            TextViewCompat.setTextAppearance(durationExercise, R.style.TextAppearance_AppCompat_Caption)

            if (exercises[i].durationExercise < DateTimeUtils.SECONDS_IN_ONE_MINUTE) {
                durationExercise.text = String.format(getString(R.string.user_details_duration_exercise_minutes_txt),
                        exercises[i].durationExercise.toString())
            } else {
                val hours = exercises[i].durationExercise / DateTimeUtils.SECONDS_IN_ONE_MINUTE
                val minutes = exercises[i].durationExercise % DateTimeUtils.SECONDS_IN_ONE_MINUTE

                durationExercise.text = String.format(getString(R.string.user_details_duration_exercise_hours_txt), hours.toString(), minutes.toString())
            }

            durationExercise.setTextSize(TypedValue.COMPLEX_UNIT_SP, (activity as Activity).resources.getDimension(R.dimen.text_default) / (activity as Activity).resources.displayMetrics.density)

            exercisesLinearLayout.addView(nameExercise, 0)
            exercisesLinearLayout.addView(durationExercise, 1)
        }

        fragment_user_program_details_exercises.addView(exercisesLinearLayout, 1)
    }
}