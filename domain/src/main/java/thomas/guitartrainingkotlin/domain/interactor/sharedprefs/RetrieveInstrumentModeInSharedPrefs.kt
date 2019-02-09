package thomas.guitartrainingkotlin.domain.interactor.sharedprefs

import io.reactivex.Single
import thomas.guitartrainingkotlin.domain.interactor.base.SingleUseCase
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class RetrieveInstrumentModeInSharedPrefs @Inject constructor(
    private val userRepository: UserRepository
) : SingleUseCase<String>() {

    override fun build(): Single<String> {
        return userRepository.retrieveInstrumentModeInSharedPrefs()
    }
}