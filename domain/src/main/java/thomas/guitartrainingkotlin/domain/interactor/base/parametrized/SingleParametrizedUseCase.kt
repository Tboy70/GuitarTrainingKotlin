package thomas.guitartrainingkotlin.domain.interactor.base.parametrized

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import thomas.guitartrainingkotlin.domain.interactor.base.UseCase

abstract class SingleParametrizedUseCase<T, P> : UseCase() {

    fun subscribe(params: P, onError: ((Throwable) -> Unit), onSuccess: ((T) -> Unit)) : Disposable {
        return build(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .subscribe(onSuccess, onError)
    }

    protected abstract fun build(params: P): Single<T>
}