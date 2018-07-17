package thomas.example.com.data.repository

import io.reactivex.Observable
import io.reactivex.internal.operators.observable.ObservableJust
import thomas.example.com.data.repository.client.ContentClient
import thomas.example.com.interactor.user.ConnectUser
import thomas.example.com.model.User
import thomas.example.com.repository.UserRepository
import java.util.concurrent.TimeUnit
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

    override fun connectUser(params: ConnectUser.Params?): Observable<User> {
        val user = User()
        return Observable.defer { Observable.just(user).delay(2000, TimeUnit.MILLISECONDS) }
    }
}