package thomas.example.com.data.repository

import io.reactivex.Observable
import thomas.example.com.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {

    override fun getIdUserInSharedPrefs(): Observable<String> {
        return Observable.defer { Observable.just("2") }
    }

    override fun setIdUserInSharedPrefs(idUser: String): Observable<Boolean> {
        return Observable.defer { Observable.just(true) }
    }
}