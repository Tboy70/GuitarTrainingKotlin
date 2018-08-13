package thomas.example.com.guitarTrainingKotlin.fragment.program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_end_program.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment

class EndProgramFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_end_program, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        continue_button.setOnClickListener {
            activity?.finish()
        }
    }
}