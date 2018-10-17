package thomas.example.com.interactor.user

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.User
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class ConnectUser @Inject constructor(
    threadExecutor: ThreadExecutor,
    private var userRepository: UserRepository
) : UseCase<User, ConnectUser.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: Params): Observable<User> {
        return userRepository.connectUser(params.user)
    }

    class Params(val user: User) {

        companion object {
            fun forLogin(user: User): Params {
                return Params(user)
            }
        }
    }
}