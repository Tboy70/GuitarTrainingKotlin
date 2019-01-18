package thomas.example.com.guitarTrainingKotlin.fragment.program

import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.viewmodel.program.IntroProgramViewModel

class IntroProgramFragment : BaseFragment<IntroProgramViewModel>() {

    override val viewModelClass = IntroProgramViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_intro_program
}