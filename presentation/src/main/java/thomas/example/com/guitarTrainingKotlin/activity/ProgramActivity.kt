package thomas.example.com.guitarTrainingKotlin.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_program.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.exercise.AbstractExerciseFragment
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.ExerciseUtils
import thomas.example.com.guitarTrainingKotlin.viewmodel.factory.ViewModelFactory
import thomas.example.com.guitarTrainingKotlin.viewmodel.program.ProgramViewModel
import thomas.example.com.model.Exercise
import javax.inject.Inject

class ProgramActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var programViewModel: ProgramViewModel

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    private lateinit var exercisesOfProgram: List<Exercise>
    private var rankExercise = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)

        programViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProgramViewModel::class.java)

        exercisesOfProgram = ArrayList()

        val extras = intent.extras

        if (extras.containsKey(ConstValues.ID_PROGRAM)) {
            programViewModel.getProgramById(extras.getString(ConstValues.ID_PROGRAM))
        }

        programViewModel.programRetrieved.observeSafe(this) {
            exercisesOfProgram = it.getExercises()
            startExercise(rankExercise)
        }

        programViewModel.errorEvent.observeSafe(this) {
            val errorTriggered = programViewModel.errorThrowable
            if (it.ERROR_TRIGGERED && errorTriggered != null) {
//                errorRendererComponent.requestRenderError(
//                        errorTriggered,
//                        ErrorRendererComponentImpl.ERROR_DISPLAY_MODE_SNACKBAR,
//                        findViewById(android.R.id.content)
//                )
            }
        }

        // TODO : Create a view state for loading ?
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

    fun setProgramToolbar(toolbarTitle: String?) {
        setSupportActionBar(activity_program_toolbar)
        activity_program_toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        activity_program_toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        activity_program_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        if (activity_program_toolbar != null) {
            activity_program_toolbar.title = toolbarTitle
        }
    }
}