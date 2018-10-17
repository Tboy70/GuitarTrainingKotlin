package thomas.example.com.interactor.user

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class SuppressAccount @Inject constructor(
    threadExecutor: ThreadExecutor,
    private var userRepository: UserRepository
) : UseCase<Boolean, SuppressAccount.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: Params): Observable<Boolean> {
        return userRepository.suppressAccount(params.idUser)
    }

    class Params(val idUser: String) {

        companion object {
            fun toSuppress(idUser: String): Params {
                return Params(idUser)
            }
        }
    }
}
