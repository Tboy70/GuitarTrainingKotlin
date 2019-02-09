package thomas.guitartrainingkotlin.presentation.fragment.program

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import kotlinx.android.synthetic.main.fragment_user_program_details.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.ProgramActivity
import thomas.guitartrainingkotlin.presentation.activity.UserProgramActivity
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.component.DialogComponentImpl
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.fragment.BaseFragment
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.DateTimeUtils
import thomas.guitartrainingkotlin.presentation.utils.ExerciseUtils
import thomas.guitartrainingkotlin.presentation.viewmodel.user.UserProgramDetailsViewModel
import thomas.guitartrainingkotlin.domain.model.Exercise
import javax.inject.Inject

class UserProgramDetailsFragment : BaseFragment<UserProgramDetailsViewModel>() {

    override val viewModelClass = UserProgramDetailsViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_program_details

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var materialDialogComponentImpl: DialogComponentImpl

    private lateinit var idProgram: String

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
        viewModel.getProgramById(idProgram)
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

        viewModel.programDetailsRetrieved.observeSafe(this) {
            displayInformation(it)
        }

//        viewModel.viewState.observeSafe(this) {
//            when {
//                it.displayingLoadingGetProgram -> dialogComponent.showProgressDialog(
//                        getString(R.string.dialog_details_program_title),
//                        getString(R.string.dialog_details_program_content),
//                        R.color.colorPrimary
//                )
//                it.displayLoadingRemoveProgram -> dialogComponent.showProgressDialog(
//                        getString(R.string.dialog_remove_program_title),
//                        getString(R.string.dialog_remove_program_content),
//                        R.color.colorPrimary
//                )
//                else -> dialogComponent.dismissDialog()
//            }
//        }

        viewModel.errorEvent.observeSafe(this) {
            val errorTriggered = viewModel.errorThrowable
            if (it.ERROR_TRIGGERED && errorTriggered != null) {
//                errorRendererComponent.requestRenderError(
//                        errorTriggered,
//                        ErrorRendererComponentImpl.ERROR_DISPLAY_MODE_SNACKBAR,
//                        view
//                )
            }
        }

        viewModel.finishProgramDeletion.observeSafe(this) {
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
//            val bundle = Bundle()
//            bundle.putSerializable(
//                    UserProgramUpdateFragment.PROGRAM_OBJECT_WRAPPER_KEY,
//                    viewModel.userProgramViewDataWrapper
//            )
//
//            val host = activity?.findViewById(R.id.user_program_nav_host_fragment) as View
//            findNavController(host).navigate(R.id.action_user_songs_list_to_user_programs_list)
        }
    }

    private fun handleRemoveProgram() {
        fragment_user_program_details_remove_button.setOnClickListener {
//            dialogComponent.showMultiChoiceDialog(
//                    getString(R.string.dialog_remove_program_title),
//                    getString(R.string.dialog_remove_program_confirm_content),
//                    R.color.colorPrimary,
//                    object : MultipleChoiceMaterialDialogListener {
//                        override fun onYesSelected() {
//                            viewModel.removeProgram(idProgram)
//                        }
//                    })
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