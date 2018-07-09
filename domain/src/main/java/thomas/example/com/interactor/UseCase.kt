package thomas.example.com.interactor

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import thomas.example.com.executor.ThreadExecutor

abstract class UseCase<ReturnType, Params> protected constructor(private val threadExecutor: ThreadExecutor) {

    private var disposable: Disposable? = null

    /**
     * Unit -> Void in java
     */
    fun execute(onComplete: (() -> Unit), onError: ((Throwable) -> Unit), onNext: ((ReturnType) -> Unit), params : Params) {
        disposable = buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .subscribe(onNext, onError, onComplete)
    }

    fun unsubscribe() {
        if (disposable?.isDisposed == false) {
            disposable?.dispose()
        }
    }

    abstract fun buildUseCaseObservable(params: Params?): Observable<ReturnType>
}
