package thomas.example.com.data.repository

import rx.Observable
import thomas.example.com.repository.UserRepository
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {

    override fun getIdUserInSharedPrefs(): Observable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setIdUserInSharedPrefs(idUser: String): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}