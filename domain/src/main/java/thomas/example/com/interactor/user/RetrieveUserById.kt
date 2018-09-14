package thomas.example.com.interactor.user

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.User
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class RetrieveUserById @Inject constructor(threadExecutor: ThreadExecutor,
                                           private var userRepository: UserRepository)
    : UseCase<User, RetrieveUserById.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: Params): Observable<User> {
        return userRepository.retrieveUserById(params.idUser)
    }

    class Params(val idUser: String) {

        companion object {
            fun toRetrieve(idUser: String): Params {
                return Params(idUser)
            }
        }
    }
}