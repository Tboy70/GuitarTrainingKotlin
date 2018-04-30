package thomas.example.com.sharedprefs

import rx.Observable
import thomas.example.com.UseCase
import thomas.example.com.executor.PostExecutionThread
import thomas.example.com.executor.ThreadExecutor
import javax.inject.Inject

class GetIdInSharedPrefs @Inject constructor(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread) : UseCase<Void>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void): Observable<Any> {
        return Observable.empty()
    }
}

//    var userRepository : UserRepository;

//    override fun buildUseCaseObservable(params: Void): Observable<Any> {
//        return userRepository.getIdInSharedPrefs()
//    }

//}