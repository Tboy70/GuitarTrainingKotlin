package thomas.guitartrainingkotlin.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import thomas.guitartrainingkotlin.domain.model.User

interface UserRepository {

    // Shared prefs
    fun retrieveUserIdInSharedPrefs(): Single<String>

    fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Single<Int>

    fun retrieveInstrumentModeInSharedPrefs(): Single<Int>

    // User
    fun connectUser(user: User): Single<User>

    fun createNewUser(user: User): Completable

    fun retrieveUserById(userId: String): Single<User>

    fun logoutUser(): Completable

    fun suppressAccount(userId: String): Completable
}
