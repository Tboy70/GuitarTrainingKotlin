package thomas.guitartrainingkotlin.domain.interactor.base

import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Completable --> If we need a confirmation that all happened great.
 * Example : Set value in shared prefs --> no need data in return but only to know if it works or not
 */
abstract class CompletableUseCase : UseCase() {

    fun subscribe(onError: ((Throwable) -> Unit), onComplete: (() -> Unit)): Disposable {
        return build()
            .subscribeOn(Schedulers.from(threadExecutor))
            .subscribe(onComplete, onError)
    }

    protected abstract fun build(): Completable
}
