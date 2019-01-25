package thomas.example.com.repository

import io.reactivex.Completable
import io.reactivex.Single
import thomas.example.com.model.User

interface UserRepository {

    // Shared prefs
    fun getUserIdInSharedPrefs(): Single<String>

    fun setInstrumentModeInSharedPrefs(): Completable


    // User
    fun connectUser(user: User): Single<User>

    fun createNewUser(user: User): Completable

    fun retrieveUserById(userId: String): Single<User>

    fun logoutUser(): Completable

    fun suppressAccount(idUser: String): Completable

}
