package thomas.example.com.interactor.sharedprefs

import io.reactivex.Single
import thomas.example.com.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.example.com.repository.UserRepository
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