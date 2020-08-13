package thomas.guitartrainingkotlin.domain.interactor.sharedprefs

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class RetrieveInstrumentModeInSharedPrefs @Inject constructor(
    private val userRepository: UserRepository
) {

    fun retrieveInstrumentModeInSharedPrefs(): Flow<Int> {
        return userRepository.retrieveInstrumentModeInSharedPrefs()
    }
}