package thomas.guitartrainingkotlin.domain.interactor.user

import io.reactivex.Completable
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class SuppressAccount @Inject constructor(
    private val userRepository: UserRepository
) : CompletableParametrizedUseCase<SuppressAccount.Params>() {

    override fun build(params: Params): Completable {
        return userRepository.suppressAccount(params.userId)
    }

    class Params(val userId: String) {

        companion object {
            fun toSuppress(userId: String): Params {
                return Params(userId)
            }
        }
    }
}
