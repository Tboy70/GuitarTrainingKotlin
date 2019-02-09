package thomas.guitartrainingkotlin.domain.interactor.sharedprefs

import io.reactivex.Single
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class SetInstrumentModeInSharedPrefs @Inject constructor(
    private val userRepository: UserRepository
) : SingleParametrizedUseCase<String, SetInstrumentModeInSharedPrefs.Params>() {

    override fun build(params: Params): Single<String> {
        return userRepository.setInstrumentModeInSharedPrefs(params.instrumentMode)
    }

    class Params(val instrumentMode: String) {

        companion object {
            fun toSet(instrumentMode: String): Params {
                return Params(instrumentMode)
            }
        }
    }
}