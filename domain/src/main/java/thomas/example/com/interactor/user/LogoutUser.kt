package thomas.example.com.interactor.user

import io.reactivex.Completable
import thomas.example.com.interactor.base.CompletableUseCase
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class LogoutUser @Inject constructor(private var userRepository: UserRepository) : CompletableUseCase() {

    override fun build(): Completable {
        return userRepository.logoutUser()
    }
}