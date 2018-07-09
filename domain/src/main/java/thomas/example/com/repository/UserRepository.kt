package thomas.example.com.repository

import io.reactivex.Observable

interface UserRepository {

    fun getIdUserInSharedPrefs(): Observable<String>

    fun setIdUserInSharedPrefs(idUser: String): Observable<Boolean>
}
