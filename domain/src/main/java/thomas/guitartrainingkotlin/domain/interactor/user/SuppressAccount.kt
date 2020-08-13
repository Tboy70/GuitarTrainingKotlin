package thomas.guitartrainingkotlin.domain.interactor.user

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class SuppressAccount @Inject constructor(
    private val userRepository: UserRepository
) {

    fun suppressAccount(userId: String): Flow<Unit> {
        return userRepository.suppressAccount(userId)
    }
}
