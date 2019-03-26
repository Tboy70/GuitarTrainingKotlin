package thomas.guitartrainingkotlin.presentation.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.fragment.exercise.AbstractExerciseFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.ExerciseUtils
import thomas.guitartrainingkotlin.presentation.viewmodel.factory.ViewModelFactory
import thomas.guitartrainingkotlin.presentation.viewmodel.program.ProgramViewModel
import javax.inject.Inject

class ProgramActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var programViewModel: ProgramViewModel

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    private lateinit var exercisesOfProgram: List<Exercise> // TODO : ExerciseViewDataWrapper

    private var rankExercise = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)

        programViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProgramViewModel::class.java)

        exercisesOfProgram = ArrayList()

        intent.extras?.let { bundle ->
            if (bundle.containsKey(ConstValues.ID_PROGRAM)) {
                bundle.getString(ConstValues.ID_PROGRAM)?.let {
                    programViewModel.getProgramById(it)
                }
            }
        }
        initiateViewModelObserver()
    }

    fun startExercise(rankExercise: Int) {
        this.rankExercise = rankExercise
        if (rankExercise < exercisesOfProgram.size) {
            val rightExercise = exercisesOfProgram[rankExercise]
            val idFragmentToLaunch = ExerciseUtils.convertTypeExerciseToIdFragment(rightExercise.typeExercise)
            val bundle = Bundle()
            bundle.putInt(AbstractExerciseFragment.RANK_EXERCISE, rankExercise)
            bundle.putInt(AbstractExerciseFragment.DURATION_EXERCISE, rightExercise.durationExercise)
            findNavController(R.id.program_nav_host_fragment).navigate(idFragmentToLaunch, bundle, null)
        } else {
            findNavController(R.id.program_nav_host_fragment).navigate(R.id.launcher_end_program_fragment, null, null)
        }
    }

    private fun initiateViewModelObserver() {
        programViewModel.programRetrievedLiveEvent.observeSafe(this) {
            exercisesOfProgram = it.getExercises()
            startExercise(rankExercise)
        }

        programViewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }
    }

//    fun setProgramToolbar(toolbarTitle: String?) {
//        setSupportActionBar(activity_program_toolbar)
//        activity_program_toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
//        activity_program_toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
//        activity_program_toolbar.setNavigationOnClickListener {
//            onBackPressed()
//        }
//
//        if (activity_program_toolbar != null) {
//            activity_program_toolbar.title = toolbarTitle
//        }
//    }
}