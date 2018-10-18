package thomas.example.com.interactor.base

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Observable --> We have more than one emit on the flow
 */
abstract class ObservableUseCase<T> : UseCase() {

    fun subscribe(onError: ((Throwable) -> Unit), onComplete: (() -> Unit), onNext: ((T) -> Unit)) {
        disposable = build()
            .subscribeOn(Schedulers.from(threadExecutor))
            .subscribe(onNext, onError, onComplete)
    }

    protected abstract fun build(): Observable<T>
}