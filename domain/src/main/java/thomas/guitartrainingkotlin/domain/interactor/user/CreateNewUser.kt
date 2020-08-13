package thomas.guitartrainingkotlin.domain.interactor.user

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.User
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class CreateNewUser @Inject constructor(
    private val userRepository: UserRepository
) {

    fun createNewUser(user: User): Flow<Unit> {
        return userRepository.createNewUser(user)
    }
}