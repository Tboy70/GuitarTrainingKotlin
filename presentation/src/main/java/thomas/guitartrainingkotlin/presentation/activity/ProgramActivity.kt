package thomas.guitartrainingkotlin.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.fragment.BaseExerciseFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.ExerciseUtils
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ExerciseViewDataWrapper
import thomas.guitartrainingkotlin.presentation.viewmodel.factory.ViewModelFactory
import thomas.guitartrainingkotlin.presentation.viewmodel.program.ProgramViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.shared.ProgramSharedViewModel
import javax.inject.Inject

class ProgramActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    private var rankExercise = 0
    private var navHost: View? = null
    private var programStarted = false
    private var exercisesOfProgram: List<ExerciseViewDataWrapper> = ArrayList()

    private lateinit var programViewModel: ProgramViewModel
    private lateinit var sharedViewModel: ProgramSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)

        navHost = findViewById(R.id.program_nav_host_fragment)
        sharedViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProgramSharedViewModel::class.java)

        programViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProgramViewModel::class.java)

        intent.extras?.let { bundle ->
            if (bundle.containsKey(ConstValues.ID_PROGRAM)) {
                bundle.getString(ConstValues.ID_PROGRAM)?.let {
                    programViewModel.getProgramById(it)
                }
            }
        }
        initiateViewModelObserver()
    }

    override fun onBackPressed() {
        sharedViewModel.onBackPressed()
    }

    fun startExercise(rankExercise: Int, nameProgram: String) {
        this.rankExercise = rankExercise
        if (rankExercise < exercisesOfProgram.size) {

            val rightExercise = exercisesOfProgram[rankExercise]
            val bundle = Bundle()
            bundle.putInt(BaseExerciseFragment.RANK_EXERCISE, rankExercise)
            bundle.putInt(BaseExerciseFragment.DURATION_EXERCISE, rightExercise.getDurationExercise())
            bundle.putString(BaseExerciseFragment.NAME_PROGRAM, nameProgram)

            if (!programStarted) {
                navHost?.findNavController()?.let {
                    val idFragmentToLaunch =
                        ExerciseUtils.convertTypeExerciseToStartIdFragment(rightExercise.getTypeExercise())
                    val graph = it.navInflater.inflate(R.navigation.program_nav_graph)
                    graph.startDestination = idFragmentToLaunch
                    it.setGraph(graph, bundle)
                    programStarted = true
                }
            } else {
                val idFragmentToLaunch = ExerciseUtils.convertTypeExerciseToIdFragment(rightExercise.getTypeExercise())
                findNavController(R.id.program_nav_host_fragment).navigate(idFragmentToLaunch, bundle, null)
            }
        } else {
            findNavController(R.id.program_nav_host_fragment).navigate(R.id.launcher_end_program_fragment, null, null)
        }
    }

    private fun initiateViewModelObserver() {
        sharedViewModel.backPressedLiveEvent.observeSafe(this) {
            if (!findNavController(R.id.program_nav_host_fragment).navigateUp()) {
                finish()
            }
        }

        programViewModel.programRetrievedLiveEvent.observeSafe(this) {
            exercisesOfProgram = it.getExercises().map { exercise ->
                ExerciseViewDataWrapper(exercise)
            }
            startExercise(rankExercise, it.getName())
        }

        programViewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }
    }
}