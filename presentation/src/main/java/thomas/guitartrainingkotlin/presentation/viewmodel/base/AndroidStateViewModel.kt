package thomas.guitartrainingkotlin.presentation.viewmodel.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent

abstract class AndroidStateViewModel<T>(
    application: Application
) : BaseAndroidViewModel(application) {
    abstract val currentViewState: T

    val viewState = MutableLiveData<T>()
    val errorLiveEvent = SingleLiveEvent<Throwable>()

    protected inline fun <reified T> MutableLiveData<T>.update(block: T.() -> Unit) {
        this.postValue((currentViewState as T).apply(block))
    }
}