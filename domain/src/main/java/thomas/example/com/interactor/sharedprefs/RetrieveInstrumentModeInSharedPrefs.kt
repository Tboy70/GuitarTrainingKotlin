package thomas.example.com.interactor.sharedprefs

import io.reactivex.Single
import thomas.example.com.interactor.base.SingleUseCase
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class RetrieveInstrumentModeInSharedPrefs @Inject constructor(
    private val userRepository: UserRepository
) : SingleUseCase<String>() {

    override fun build(): Single<String> {
        return userRepository.retrieveInstrumentModeInSharedPrefs()
    }
}