package thomas.example.com.interactor

import io.reactivex.Observable
import thomas.example.com.executor.PostExecutionThread
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class GetIdInSharedPrefs @Inject constructor(threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread,
                                             private var userRepository: UserRepository)
    : UseCase<String, String>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: String?): Observable<String> {
       return userRepository.getIdUserInSharedPrefs()
    }
}