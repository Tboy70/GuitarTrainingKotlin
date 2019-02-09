package thomas.guitartrainingkotlin.domain.interactor.user

import io.reactivex.Completable
import thomas.guitartrainingkotlin.domain.interactor.base.CompletableUseCase
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUser @Inject constructor(private var userRepository: UserRepository) : CompletableUseCase() {

    override fun build(): Completable {
        return userRepository.logoutUser()
    }
}