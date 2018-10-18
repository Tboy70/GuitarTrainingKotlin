package thomas.example.com.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import thomas.example.com.model.User

interface UserRepository {

    fun getIdUserInSharedPrefs(): Single<String>

    fun setIdUserInSharedPrefs(idUser: String?): Observable<Boolean>

    fun connectUser(user: User): Single<User>

    fun createNewUser(user: User): Completable

    fun logoutUser(): Completable

    fun retrieveUserById(idUser: String): Single<User>

    fun setInstrumentModeInSharedPrefs(): Observable<Boolean>

    fun suppressAccount(idUser: String): Observable<Boolean>
}
