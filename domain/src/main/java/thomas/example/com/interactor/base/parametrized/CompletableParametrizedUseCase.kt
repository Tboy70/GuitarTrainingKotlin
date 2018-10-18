package thomas.example.com.interactor.base.parametrized

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import thomas.example.com.interactor.base.UseCase

abstract class CompletableParametrizedUseCase<P> : UseCase() {

    fun subscribe(params: P, onError: ((Throwable) -> Unit), onComplete: (() -> Unit)) {
        disposable = build(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .subscribe(onComplete, onError)
    }

    protected abstract fun build(params: P): Completable
}