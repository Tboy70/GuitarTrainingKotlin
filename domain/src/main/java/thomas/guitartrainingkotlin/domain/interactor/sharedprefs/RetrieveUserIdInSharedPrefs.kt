package thomas.guitartrainingkotlin.domain.interactor.sharedprefs

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class RetrieveUserIdInSharedPrefs @Inject constructor(
    private val userRepository: UserRepository
) {

    fun retrieveUserIdInSharedPrefs(): Flow<String?> {
        return userRepository.retrieveUserIdInSharedPrefs()
    }
}