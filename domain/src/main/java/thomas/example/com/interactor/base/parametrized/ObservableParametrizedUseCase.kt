package thomas.example.com.interactor.base.parametrized

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import thomas.example.com.interactor.base.UseCase

abstract class ObservableParametrizedUseCase<T, P> : UseCase() {

    fun subscribe(params: P, onError: ((Throwable) -> Unit), onComplete: (() -> Unit), onNext: ((T) -> Unit)) {
        disposable = build(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .subscribe(onNext, onError, onComplete)
    }

    protected abstract fun build(params: P): Observable<T>
}