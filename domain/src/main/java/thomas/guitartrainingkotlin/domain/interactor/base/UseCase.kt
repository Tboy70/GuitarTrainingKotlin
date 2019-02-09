package thomas.guitartrainingkotlin.domain.interactor.base

import io.reactivex.disposables.Disposable
import thomas.guitartrainingkotlin.domain.executor.ThreadExecutor
import javax.inject.Inject

abstract class UseCase {

    @Inject
    protected lateinit var threadExecutor: ThreadExecutor

    protected var disposable: Disposable? = null

    fun unsubscribe() {
        if (disposable?.isDisposed == false) {
            disposable?.dispose()
        }
    }
}
