package thomas.example.com.interactor.user

import io.reactivex.Completable
import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.base.UseCase
import thomas.example.com.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class SuppressAccount @Inject constructor(
    private var userRepository: UserRepository
) : CompletableParametrizedUseCase<SuppressAccount.Params>() {

    override fun build(params: Params): Completable {
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
