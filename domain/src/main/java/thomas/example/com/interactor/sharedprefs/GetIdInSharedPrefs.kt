package thomas.example.com.interactor.sharedprefs

import io.reactivex.Single
import thomas.example.com.interactor.base.SingleUseCase
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class GetIdInSharedPrefs @Inject constructor(
        private var userRepository: UserRepository
) : SingleUseCase<String>() {

    companion object {
        var ID_USER_DEFAULT = "ID_USER_DEFAULT"
    }

    override fun build(): Single<String> {
        return userRepository.getIdUserInSharedPrefs()
    }
}