package thomas.guitartrainingkotlin.domain.interactor.sharedprefs

import io.reactivex.Single
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class SetInstrumentModeInSharedPrefs @Inject constructor(
    private val userRepository: UserRepository
) : SingleParametrizedUseCase<Int, SetInstrumentModeInSharedPrefs.Params>() {

    override fun build(params: Params): Single<Int> {
        return userRepository.setInstrumentModeInSharedPrefs(params.instrumentMode)
    }

    class Params(val instrumentMode: Int) {

        companion object {
            fun toSet(instrumentMode: Int): Params {
                return Params(instrumentMode)
            }
        }
    }
}