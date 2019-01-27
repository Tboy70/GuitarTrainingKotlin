package thomas.example.com.guitarTrainingKotlin.fragment.other

import android.os.Bundle
import android.view.View
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.viewmodel.other.LegalNoticesViewModel

class LegalNoticesFragment : BaseFragment<LegalNoticesViewModel>() {

    override val viewModelClass = LegalNoticesViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_legal_notices

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateToolbar()
    }

    private fun initiateToolbar() {
        (activity as UserPanelActivity).setToolbar(activity?.getString(R.string.user_panel_navigation_drawer_legal_notices))
    }
}