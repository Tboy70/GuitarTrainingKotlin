package thomas.guitartrainingkotlin.presentation.fragment.program

import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.fragment.BaseFragment
import thomas.guitartrainingkotlin.presentation.viewmodel.program.IntroProgramViewModel

class IntroProgramFragment : BaseFragment<IntroProgramViewModel>() {

    override val viewModelClass = IntroProgramViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_intro_program
}