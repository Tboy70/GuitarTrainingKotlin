package thomas.example.com.interactor.user

import io.reactivex.Completable
import thomas.example.com.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class SuppressAccount @Inject constructor(
    private var userRepository: UserRepository
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
