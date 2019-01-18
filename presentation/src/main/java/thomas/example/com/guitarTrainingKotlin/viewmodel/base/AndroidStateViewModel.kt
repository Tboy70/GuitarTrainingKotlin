package thomas.example.com.guitarTrainingKotlin.viewmodel.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent

abstract class AndroidStateViewModel<T>(
    application: Application
) : AndroidViewModel(application) {
    abstract val currentViewState: T

    val viewState = MutableLiveData<T>()
    val errorLiveEvent = SingleLiveEvent<Throwable>()

    protected inline fun <reified T> MutableLiveData<T>.update(block: T.() -> Unit) {
        this.postValue((currentViewState as T).apply(block))
    }
}