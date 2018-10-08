package thomas.example.com.guitarTrainingKotlin.fragment.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserPanelActivity
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment

class LegalNoticesFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_legal_notices, container, false)
    }

    override fun onStart() {
        super.onStart()
        (activity as UserPanelActivity).setToolbar((activity as UserPanelActivity).getString(R.string.user_panel_navigation_drawer_legal_notices))
    }
}