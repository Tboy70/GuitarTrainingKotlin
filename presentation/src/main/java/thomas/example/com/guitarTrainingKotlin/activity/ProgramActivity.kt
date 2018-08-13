package thomas.example.com.guitarTrainingKotlin.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.ExerciseUtils
import thomas.example.com.guitarTrainingKotlin.viewmodel.ProgramViewModel
import thomas.example.com.model.Exercise
import javax.inject.Inject

class ProgramActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var programViewModel: ProgramViewModel

    private lateinit var host: NavHostFragment

    private lateinit var exercisesOfProgram: List<Exercise>
    private var rankExercise = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)

        programViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProgramViewModel::class.java)

        host = supportFragmentManager.findFragmentById(R.id.program_nav_host_fragment) as NavHostFragment

        exercisesOfProgram = ArrayList()

        val extras = intent.extras

        if (extras.containsKey(ConstValues.ID_PROGRAM)) {
            programViewModel.getProgramById(extras.getString(ConstValues.ID_PROGRAM))
        }

        programViewModel.finishRetrieveProgram.observe(this, Observer<Boolean> {
            if (it == true) {
                val userProgramObjectWrapper = programViewModel.userProgramObjectWrapper
                exercisesOfProgram = userProgramObjectWrapper.program.exercises
                startExercise(rankExercise)
            }
        })
    }

    fun startExercise(rankExercise: Int) {
        if (rankExercise < exercisesOfProgram.size) {
            val idFragmentToLaunch = ExerciseUtils.convertTypeExerciseToIdFragment(exercisesOfProgram[rankExercise].idExercise.toInt())
            val bundle = Bundle()
            bundle.putInt(ConstValues.RANK_EXERCISE, rankExercise)
            NavHostFragment.findNavController(host).navigate(idFragmentToLaunch, bundle, null)
        } else {
            NavHostFragment.findNavController(host).navigate(R.id.launcher_end_program_fragment, null, null)
        }
    }
}