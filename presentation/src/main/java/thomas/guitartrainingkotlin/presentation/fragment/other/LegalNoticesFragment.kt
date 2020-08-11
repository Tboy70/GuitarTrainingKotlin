package thomas.guitartrainingkotlin.presentation.fragment.other

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.activity.UserPanelActivity

@AndroidEntryPoint
class LegalNoticesFragment : Fragment(R.layout.fragment_legal_notices) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateToolbar()
    }

    private fun initiateToolbar() {
        (activity as UserPanelActivity).setToolbar(activity?.getString(R.string.user_panel_navigation_drawer_legal_notices))
    }
}