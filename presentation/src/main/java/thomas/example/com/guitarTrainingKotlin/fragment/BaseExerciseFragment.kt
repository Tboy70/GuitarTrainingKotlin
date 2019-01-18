package thomas.example.com.guitarTrainingKotlin.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

// TODO : See if we can use BaseFragment instead
abstract class BaseExerciseFragment : Fragment() {

    override fun onAttach(context: Context?) {
        /** Call the AndroidSupportInjection to inject fragment. **/
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}