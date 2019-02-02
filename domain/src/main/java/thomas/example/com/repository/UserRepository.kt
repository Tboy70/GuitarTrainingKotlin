package thomas.example.com.repository

import io.reactivex.Completable
import io.reactivex.Single
import thomas.example.com.model.User

interface UserRepository {

    // Shared prefs
    fun getUserIdInSharedPrefs(): Single<String>

    fun setInstrumentModeInSharedPrefs(instrumentMode: String): Single<String>

    fun retrieveInstrumentModeInSharedPrefs(): Single<String>

    // User
    fun connectUser(user: User): Single<User>

    fun createNewUser(user: User): Completable

    fun retrieveUserById(userId: String): Single<User>

    fun logoutUser(): Completable

    fun suppressAccount(userId: String): Completable

}
