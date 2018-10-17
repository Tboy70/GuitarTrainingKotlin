package thomas.example.com.guitarTrainingKotlin.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

    override fun onAttach(context: Context?) {
        /** Call the AndroidSupportInjection to inject fragment. **/
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}