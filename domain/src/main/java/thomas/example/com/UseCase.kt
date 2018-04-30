package thomas.example.com

import rx.Observable
import rx.Subscriber
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions
import thomas.example.com.executor.PostExecutionThread
import thomas.example.com.executor.ThreadExecutor

abstract class UseCase<Params> protected constructor(private val threadExecutor: ThreadExecutor, private val postExecutionThread: PostExecutionThread) {

    private var subscription  = Subscriptions.empty()

    /**
     * Be careful --> Any ~= Object class in java --> Mandatory
     */
    fun execute(useCaseSubscriber : Subscriber<Any>, params : Params) {
        this.subscription = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(useCaseSubscriber)
    }

    fun unsubscribe() {
        if (subscription.isUnsubscribed) {
            subscription.unsubscribe()
        }
    }

    abstract fun buildUseCaseObservable(params: Params) : Observable<Any>
}