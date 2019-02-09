package thomas.guitartrainingkotlin.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import thomas.guitartrainingkotlin.presentation.viewmodel.factory.ViewModelFactory
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<T : ViewModel> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: T

    abstract val viewModelClass: KClass<T>

    override fun onAttach(context: Context?) {
        /** Call the AndroidSupportInjection to inject fragment. **/
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass.java)
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
}