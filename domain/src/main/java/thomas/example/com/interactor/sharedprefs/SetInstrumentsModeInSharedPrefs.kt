package thomas.example.com.interactor.sharedprefs

import io.reactivex.Completable
import thomas.example.com.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class SetInstrumentsModeInSharedPrefs @Inject constructor(
    private val userRepository: UserRepository
) : CompletableParametrizedUseCase<SetInstrumentsModeInSharedPrefs.Params>() {

    override fun build(params: Params): Completable {
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