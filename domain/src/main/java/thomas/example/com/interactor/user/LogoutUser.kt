package thomas.example.com.interactor.user

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class LogoutUser @Inject constructor(
    threadExecutor: ThreadExecutor,
    private var userRepository: UserRepository
) : UseCase<Boolean, Unit>(threadExecutor) {

    override fun buildUseCaseObservable(params: Unit): Observable<Boolean> {
        return userRepository.logoutUser()
    }
}