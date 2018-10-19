package thomas.example.com.interactor.sharedprefs

import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.base.CompletableUseCase
import thomas.example.com.interactor.base.UseCase
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class SetInstrumentsModeInSharedPrefs @Inject constructor(
    private var userRepository: UserRepository
) : CompletableUseCase() {

    override fun build(): Completable {
        return userRepository.setInstrumentModeInSharedPrefs()
    }
}