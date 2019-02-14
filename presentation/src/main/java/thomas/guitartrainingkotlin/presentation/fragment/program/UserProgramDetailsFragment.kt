package thomas.guitartrainingkotlin.presentation.fragment.program

import android.app.Activity
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
import kotlinx.android.synthetic.main.fragment_user_program_details.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.presentation.component.DialogComponentImpl
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.fragment.BaseFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.DateTimeUtils
import thomas.guitartrainingkotlin.presentation.utils.ExerciseUtils
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.viewmodel.user.UserProgramDetailsViewModel
import javax.inject.Inject

class UserProgramDetailsFragment : BaseFragment<UserProgramDetailsViewModel>() {

    override val viewModelClass = UserProgramDetailsViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_program_details

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var dialogComponent: DialogComponentImpl

    private var navHost: View? = null

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
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(true)
        activity?.setSupportActionBar(fragment_user_program_details_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateView() {
        fragment_user_program_details_start_button.setOnClickListener {
            //            val intent = Intent(activity, ProgramActivity::class.java)
//            intent.putExtra(ConstValues.ID_PROGRAM, idProgram)
//            startActivity(intent)
//            activity?.finish()
        }

        fragment_user_program_details_update_button.setOnClickListener {
            //            val bundle = Bundle()
//            bundle.putSerializable(
//                    UserProgramUpdateFragment.PROGRAM_OBJECT_WRAPPER_KEY,
//                    viewModel.userProgramViewDataWrapper
//            )
//
//            val host = baseActivity?.findViewById(R.id.user_program_nav_host_fragment) as View
//            findNavController(host).navigate(R.id.action_user_songs_list_to_user_programs_list)
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
                onNegative = {}
            )
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.songRetrievedLiveData.observeSafe(this) {
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