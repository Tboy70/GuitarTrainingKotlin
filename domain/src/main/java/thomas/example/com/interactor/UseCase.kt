package thomas.example.com.interactor

import rx.Observable
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions
import thomas.example.com.executor.PostExecutionThread
import thomas.example.com.executor.ThreadExecutor

abstract class UseCase<ReturnType, Params> protected constructor(private val threadExecutor: ThreadExecutor, private val postExecutionThread: PostExecutionThread) {

    private var subscription = Subscriptions.empty()

    /**
     * Be careful --> Any ~= Object class in java --> Mandatory
     */
    fun execute(UseCaseSubscriber: rx.Subscriber<ReturnType>, params: Params?) {
        this.subscription = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(UseCaseSubscriber)
    }

    fun unsubscribe() {
        if (subscription.isUnsubscribed) {
            subscription.unsubscribe()
        }
    }

    abstract fun buildUseCaseObservable(params: Params?): Observable<ReturnType>
}
