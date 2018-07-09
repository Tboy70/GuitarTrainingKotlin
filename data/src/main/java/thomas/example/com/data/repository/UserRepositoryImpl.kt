package thomas.example.com.data.repository

import io.reactivex.Observable
import thomas.example.com.data.repository.client.ContentClient
import thomas.example.com.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(private var contentClient : ContentClient) : UserRepository {

    /**
     * No need of try / catch anymore !
     */
    override fun getIdUserInSharedPrefs(): Observable<String> {
        return Observable.defer { contentClient.getIdInSharedPrefs() }
    }

    override fun setIdUserInSharedPrefs(idUser: String): Observable<Boolean> {
        return Observable.defer { Observable.just(true) }
    }
}