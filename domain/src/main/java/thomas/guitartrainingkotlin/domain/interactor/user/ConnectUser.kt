package thomas.guitartrainingkotlin.domain.interactor.user

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.User
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class ConnectUser @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun connectUser(user: User): Flow<User> {
        return userRepository.connectUser(user)
    }
}