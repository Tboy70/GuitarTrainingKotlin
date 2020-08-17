package thomas.guitartrainingkotlin.domain.interactor.sharedprefs

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class RetrieveUserId @Inject constructor(
    private val userRepository: UserRepository
) {

    fun retrieveUserId(): Flow<String?> {
        return userRepository.retrieveUserId()
    }
}