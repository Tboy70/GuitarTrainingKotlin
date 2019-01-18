package thomas.example.com.guitarTrainingKotlin.viewmodel.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent

abstract class StateViewModel<T> : ViewModel() {
    abstract val currentViewState: T

    val viewState = MutableLiveData<T>()
    val errorLiveEvent = SingleLiveEvent<Throwable>()

    protected inline fun <reified T> MutableLiveData<T>.update(block: T.() -> Unit) {
        this.postValue((currentViewState as T).apply(block))
    }
}
