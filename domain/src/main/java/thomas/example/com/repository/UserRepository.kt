package thomas.example.com.repository

import io.reactivex.Observable
import thomas.example.com.model.User

interface UserRepository {

    fun getIdUserInSharedPrefs(): Observable<String>

    fun setIdUserInSharedPrefs(idUser: String?): Observable<Boolean>

    fun connectUser(user: User): Observable<User>

    fun createNewUser(user: User): Observable<String>

    fun logoutUser(): Observable<Boolean>

    fun retrieveUserById(idUser: String): Observable<User>
}
