package thomas.example.com.interactor.base

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Single --> We need only one emit on the RX flow
 */
abstract class SingleUseCase<T> : UseCase() {

    fun subscribe(onError: ((Throwable) -> Unit), onSuccess: ((T) -> Unit)) {
        disposable = build()
            .subscribeOn(Schedulers.from(threadExecutor))
            .subscribe(onSuccess, onError)
    }

    protected abstract fun build(): Single<T>
}