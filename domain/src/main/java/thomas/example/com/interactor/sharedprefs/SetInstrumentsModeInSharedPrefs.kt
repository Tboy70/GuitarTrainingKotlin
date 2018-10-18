package thomas.example.com.interactor.sharedprefs

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.base.UseCase
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class SetInstrumentsModeInSharedPrefs @Inject constructor(
    threadExecutor: ThreadExecutor,
    private var userRepository: UserRepository
) : UseCase<Boolean, Unit>(threadExecutor) {

    override fun buildUseCaseObservable(params: Unit): Observable<Boolean> {
        return userRepository.setInstrumentModeInSharedPrefs()
    }
}