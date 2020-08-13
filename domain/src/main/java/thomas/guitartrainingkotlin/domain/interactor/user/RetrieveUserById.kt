package thomas.guitartrainingkotlin.domain.interactor.user

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.User
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class RetrieveUserById @Inject constructor(
    private val userRepository: UserRepository
) {

    fun retrieveUserById(userId: String): Flow<User> {
        return userRepository.retrieveUserById(userId)
    }
}