package thomas.example.com.interactor.sharedprefs

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class GetIdInSharedPrefs @Inject constructor(threadExecutor: ThreadExecutor,
                                             private var userRepository: UserRepository)
    : UseCase<String, String>(threadExecutor) {

    companion object {
        var ID_USER_DEFAULT = "ID_USER_DEFAULT"
    }

    override fun buildUseCaseObservable(params: String): Observable<String> {
       return userRepository.getIdUserInSharedPrefs()
    }
}