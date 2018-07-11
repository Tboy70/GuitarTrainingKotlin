package thomas.example.com.guitarTrainingKotlin.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import thomas.example.com.guitarTrainingKotlin.R

class LoginHomeFragment : Fragment() {

    companion object {

        //Todo : Is new instance really useful as we have the navigation view now ?
        fun newInstance(): LoginHomeFragment {
            return LoginHomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_home, container, false)
    }

}