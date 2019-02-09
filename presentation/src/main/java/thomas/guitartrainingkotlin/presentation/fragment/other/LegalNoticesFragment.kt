package thomas.guitartrainingkotlin.presentation.fragment.other

import android.os.Bundle
import android.view.View
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.UserPanelActivity
import thomas.guitartrainingkotlin.presentation.fragment.BaseFragment
import thomas.guitartrainingkotlin.presentation.viewmodel.other.LegalNoticesViewModel

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