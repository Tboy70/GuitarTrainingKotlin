package thomas.example.com.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import thomas.example.com.model.User

interface UserRepository {

    fun getUserIdInSharedPrefs(): Single<String>

    fun setUserIdInSharedPrefs(userId: String?): Observable<Boolean>

    fun connectUser(user: User): Single<User>

    fun createNewUser(user: User): Completable

    fun logoutUser(): Completable

    fun retrieveUserById(idUser: String): Single<User>

    fun setInstrumentModeInSharedPrefs(): Completable

    fun suppressAccount(idUser: String): Completable
}
