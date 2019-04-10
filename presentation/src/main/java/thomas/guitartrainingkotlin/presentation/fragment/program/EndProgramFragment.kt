package thomas.guitartrainingkotlin.presentation.fragment.program

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_end_program.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.fragment.BaseExerciseFragment
import thomas.guitartrainingkotlin.presentation.viewmodel.program.EndProgramViewModel

class EndProgramFragment : BaseExerciseFragment<EndProgramViewModel>() {

    override val viewModelClass = EndProgramViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_end_program

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_exercise_end_program_quit_button.setOnClickListener {
            activity?.finish()
        }
    }
}