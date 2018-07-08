package thomas.example.com.interactor

import rx.Observable
import thomas.example.com.executor.PostExecutionThread
import thomas.example.com.executor.ThreadExecutor
import javax.inject.Inject

class GetIdInSharedPrefs @Inject constructor(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread)
    : UseCase<Void>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void): Observable<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}