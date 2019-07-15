package thomas.guitartrainingkotlin.domain.interactor.base

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Observable --> We have more than one emit on the flow
 */
abstract class ObservableUseCase<T> : UseCase() {

    fun subscribe(onError: ((Throwable) -> Unit), onComplete: (() -> Unit), onNext: ((T) -> Unit)): Disposable {
        return build()
            .subscribeOn(Schedulers.from(threadExecutor))
            .subscribe(onNext, onError, onComplete)
    }

    protected abstract fun build(): Observable<T>
}