package thomas.example.com.interactor.user

import io.reactivex.Single
import thomas.example.com.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.example.com.model.User
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class ConnectUser @Inject constructor(
        private val userRepository: UserRepository
) : SingleParametrizedUseCase<User, ConnectUser.Params>() {

    override fun build(params: Params): Single<User> {
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