package thomas.example.com.guitarTrainingKotlin.fragment.other

import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.viewmodel.other.LegalNoticesViewModel

class LegalNoticesFragment : BaseFragment<LegalNoticesViewModel>() {

    override val viewModelClass = LegalNoticesViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_legal_notices

    override fun onStart() {
        super.onStart()
        (activity as UserPanelActivity).setToolbar((activity as UserPanelActivity).getString(R.string.user_panel_navigation_drawer_legal_notices))
    }
}