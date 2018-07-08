package thomas.example.com.repository

import rx.Observable

interface UserRepository {

    fun getIdUserInSharedPrefs(): Observable<String>

    fun setIdUserInSharedPrefs(idUser: String): Observable<Boolean>
}
