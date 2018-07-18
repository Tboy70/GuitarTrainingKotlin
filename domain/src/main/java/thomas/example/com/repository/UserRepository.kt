package thomas.example.com.repository

import io.reactivex.Observable
import thomas.example.com.interactor.user.ConnectUser
import thomas.example.com.model.User

interface UserRepository {

    fun getIdUserInSharedPrefs(): Observable<String>

    fun setIdUserInSharedPrefs(idUser: String?): Observable<Boolean>

    fun connectUser(params: ConnectUser.Params): Observable<User>
}
