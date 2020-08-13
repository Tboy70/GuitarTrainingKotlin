package thomas.guitartrainingkotlin.domain.interactor.sharedprefs

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class SetInstrumentModeInSharedPrefs @Inject constructor(
    private val userRepository: UserRepository
) {

    fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Flow<Int> {
        return userRepository.setInstrumentModeInSharedPrefs(instrumentMode)
    }

}