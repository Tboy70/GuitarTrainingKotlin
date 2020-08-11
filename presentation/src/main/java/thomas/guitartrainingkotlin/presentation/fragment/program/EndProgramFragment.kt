package thomas.guitartrainingkotlin.presentation.fragment.program

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_end_program.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.fragment.BaseExerciseFragment

@AndroidEntryPoint
class EndProgramFragment : BaseExerciseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_end_program

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_exercise_end_program_quit_button.setOnClickListener {
            activity?.finish()
        }
    }
}