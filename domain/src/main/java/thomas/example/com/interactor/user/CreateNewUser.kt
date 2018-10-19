package thomas.example.com.interactor.user

import io.reactivex.Completable
import thomas.example.com.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.example.com.model.User
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class CreateNewUser @Inject constructor(
        private var userRepository: UserRepository
) : CompletableParametrizedUseCase<CreateNewUser.Params>() {

    override fun build(params: Params): Completable {
        return userRepository.createNewUser(params.user)
    }

    class Params(val user: User) {

        companion object {
            fun toCreate(user: User): Params {
                return Params(user)
            }
        }
    }
}