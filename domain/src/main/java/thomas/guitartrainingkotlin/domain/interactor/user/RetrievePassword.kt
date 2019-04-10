package thomas.guitartrainingkotlin.domain.interactor.user

import io.reactivex.Completable
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class RetrievePassword @Inject constructor(
    private val userRepository: UserRepository
) : CompletableParametrizedUseCase<RetrievePassword.Params>() {

    override fun build(params: Params): Completable {
        return userRepository.retrievePassword(params.emailAddress)
    }

    class Params(val emailAddress: String) {

        companion object {
            fun toRetrieve(emailAddress: String): Params {
                return Params(emailAddress)
            }
        }
    }
}