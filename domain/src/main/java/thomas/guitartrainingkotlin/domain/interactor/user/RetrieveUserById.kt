package thomas.guitartrainingkotlin.domain.interactor.user

import io.reactivex.Single
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.guitartrainingkotlin.domain.model.User
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class RetrieveUserById @Inject constructor(
        private var userRepository: UserRepository
) : SingleParametrizedUseCase<User, RetrieveUserById.Params>() {

    override fun build(params: Params): Single<User> {
        return userRepository.retrieveUserById(params.userId)
    }

    class Params(val userId: String) {

        companion object {
            fun toRetrieve(userId: String): Params {
                return Params(userId)
            }
        }
    }
}