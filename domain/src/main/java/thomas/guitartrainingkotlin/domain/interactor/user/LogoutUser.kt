package thomas.guitartrainingkotlin.domain.interactor.user

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUser @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun logoutUser(): Flow<Unit> {
        return userRepository.logoutUser()
    }
}