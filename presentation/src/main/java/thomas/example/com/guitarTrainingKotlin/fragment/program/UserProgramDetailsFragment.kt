package thomas.example.com.guitarTrainingKotlin.fragment.program

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import kotlinx.android.synthetic.main.fragment_user_program_details.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.ProgramActivity
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.ProgramViewDataWrapper
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
    lateinit var errorRendererComponent: ErrorRendererComponent

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

        handleLiveData(view)
        handleStartProgram()
        handleUpdateProgram()
        handleRemoveProgram()
    }

    override fun onStart() {
        super.onStart()
        userProgramDetailsViewModel.getProgramById(idProgram)
    }

    private fun checkProgramAndHideButtons(idProgram: String?) {
        if (idProgram == ConstValues.DEFAULT_PROGRAM_THEORETICAL_GUITAR || idProgram == ConstValues.DEFAULT_PROGRAM_PRACTICAL_GUITAR ||
            idProgram == ConstValues.DEFAULT_PROGRAM_THEORETICAL_BASS || idProgram == ConstValues.DEFAULT_PROGRAM_PRACTICAL_BASS
        ) {

            fragment_user_program_details_remove_button.visibility = View.GONE
            fragment_user_program_details_update_button.visibility = View.GONE

            val constraintSet = ConstraintSet()
            constraintSet.clone(fragment_user_program_details_constraint_layout)
            constraintSet.clear(R.id.fragment_user_program_details_start_button, ConstraintSet.TOP)
            constraintSet.connect(
                    R.id.fragment_user_program_details_start_button,
                    ConstraintSet.BOTTOM,
                    R.id.fragment_user_program_details_constraint_layout,
                    ConstraintSet.BOTTOM,
                    24  // TODO : WTF !
            )
            constraintSet.applyTo(fragment_user_program_details_constraint_layout)
        }
    }

    private fun handleLiveData(view: View) {

        userProgramDetailsViewModel.programDetailsRetrieved.observeSafe(this) {
            displayInformation(it)
        }

        userProgramDetailsViewModel.viewState.observeSafe(this) {
            when {
                it.displayingLoadingGetProgram -> materialDialogComponent.showProgressDialog(
                        getString(R.string.dialog_details_program_title),
                        getString(R.string.dialog_details_program_content),
                        R.color.colorPrimary
                )
                it.displayLoadingRemoveProgram -> materialDialogComponent.showProgressDialog(
                        getString(R.string.dialog_remove_program_title),
                        getString(R.string.dialog_remove_program_content),
                        R.color.colorPrimary
                )
                else -> materialDialogComponent.dismissDialog()
            }
        }

        userProgramDetailsViewModel.errorEvent.observeSafe(this) {
            val errorTriggered = userProgramDetailsViewModel.errorThrowable
            if (it.ERROR_TRIGGERED && errorTriggered != null) {
                errorRendererComponent.requestRenderError(
                        errorTriggered,
                        ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR,
                        view
                )
            }
        }

        userProgramDetailsViewModel.finishProgramDeletion.observeSafe(this) {
            activity?.finish()
        }
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
            bundle.putSerializable(
                    UserProgramUpdateFragment.PROGRAM_OBJECT_WRAPPER_KEY,
                    userProgramDetailsViewModel.userProgramViewDataWrapper
            )

            val host = activity?.findViewById(R.id.user_program_nav_host_fragment) as View
            findNavController(host).navigate(R.id.action_user_songs_list_to_user_programs_list)
        }
    }

    private fun handleRemoveProgram() {
        fragment_user_program_details_remove_button.setOnClickListener {
            materialDialogComponent.showMultiChoiceDialog(
                    getString(R.string.dialog_remove_program_title),
                    getString(R.string.dialog_remove_program_confirm_content),
                    R.color.colorPrimary,
                    object : MultipleChoiceMaterialDialogListener {
                        override fun onYesSelected() {
                            userProgramDetailsViewModel.removeProgram(idProgram)
                        }
                    })
        }
    }

    private fun displayInformation(userProgramViewDataWrapper: ProgramViewDataWrapper) {

        val nameProgram = userProgramViewDataWrapper.getName()
        val descriptionProgram = userProgramViewDataWrapper.getDescription()

        setToolbar(nameProgram)

        fragment_user_program_details_name.text = nameProgram

        if (descriptionProgram.isEmpty()) {
            fragment_user_program_details_description.text =
                    activity?.getString(R.string.user_details_program_no_description)
        } else {
            fragment_user_program_details_description.text = descriptionProgram
        }

        createExercisesList(userProgramViewDataWrapper.getExercises())
    }

    private fun setToolbar(nameProgram: String) {
        if (activity is UserProgramActivity) {
            (activity as UserProgramActivity).setToolbar(nameProgram)
        }
    }

    private fun createExercisesList(exercises: MutableList<Exercise>) {
        val exercisesLinearLayout = LinearLayout(activity)
        exercisesLinearLayout.orientation = LinearLayout.VERTICAL

        val layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        exercisesLinearLayout.layoutParams = layoutParams

        for (i in exercises.size - 1 downTo 0) {
            val nameExercise = TextView(activity)
            nameExercise.text = ExerciseUtils.convertTypeExerciseToName(exercises[i].typeExercise, activity as Activity)
            nameExercise.setTextColor(ContextCompat.getColor(activity as Activity, android.R.color.black))
            nameExercise.setTextSize(
                    TypedValue.COMPLEX_UNIT_SP,
                    (activity as Activity).resources.getDimension(R.dimen.text_default) / (activity as Activity).resources.displayMetrics.density
            )

            val durationExercise = TextView(activity)
            TextViewCompat.setTextAppearance(durationExercise, R.style.TextAppearance_AppCompat_Caption)

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

        fragment_user_program_details_exercises.addView(exercisesLinearLayout, 1)
    }
}