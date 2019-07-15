package thomas.guitartrainingkotlin.presentation.viewmodel.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseAndroidViewModel(
    application: Application
) : AndroidViewModel(application) {

    protected var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }
}