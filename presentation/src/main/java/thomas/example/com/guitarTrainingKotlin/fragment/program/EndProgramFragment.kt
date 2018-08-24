package thomas.example.com.guitarTrainingKotlin.fragment.program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_end_program.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.fragment.exercise.AbstractExerciseFragment

class EndProgramFragment : AbstractExerciseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_end_program, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_exercise_end_program_quit_button.setOnClickListener {
            activity?.finish()
        }

        setToolbar(R.string.toolbar_title_end_program)
    }
}