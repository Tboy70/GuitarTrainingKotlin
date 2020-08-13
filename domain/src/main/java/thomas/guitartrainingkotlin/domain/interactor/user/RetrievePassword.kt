package thomas.guitartrainingkotlin.domain.interactor.user

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class RetrievePassword @Inject constructor(
    private val userRepository: UserRepository
) {

    fun retrievePassword(emailAddress: String): Flow<Unit> {
        return userRepository.retrievePassword(emailAddress)
    }
}