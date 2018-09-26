package thomas.example.com.data.repository

import io.reactivex.Observable
import thomas.example.com.data.mapper.UserEntityDataMapper
import thomas.example.com.data.repository.client.APIClient
import thomas.example.com.data.repository.client.ContentClient
import thomas.example.com.model.User
import thomas.example.com.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(private val userEntityDataMapper: UserEntityDataMapper,
                                             private val contentClient: ContentClient,
                                             private val apiClient: APIClient) : UserRepository {

    /**
     * No need of try / catch anymore !
     */
    override fun getIdUserInSharedPrefs(): Observable<String> {
        return Observable.defer { contentClient.getIdInSharedPrefs() }
    }

    override fun setIdUserInSharedPrefs(idUser: String?): Observable<Boolean> {
        return Observable.defer { Observable.just(true) }
    }

    override fun connectUser(user: User): Observable<User> {
        return Observable.defer {
            apiClient.connectUser(userEntityDataMapper.transformModelToEntity(user))?.map {
                userEntityDataMapper.transformEntityToModel(it)
            }?.doOnNext {
                contentClient.setIdInSharedPrefs(it.idUser)
            }
        }
    }

    override fun setInstrumentModeInSharedPrefs(): Observable<Boolean> {
        return Observable.defer {
            contentClient.setInstrumentModeInSharedPrefs()
        }
    }

    override fun retrieveUserById(idUser: String): Observable<User> {
        return Observable.defer {
            apiClient.retrieveUserById(idUser).map {
                userEntityDataMapper.transformEntityToModel(it)
            }
        }
    }

    override fun createNewUser(user: User): Observable<String> {
        return Observable.defer {
            apiClient.createNewUser(userEntityDataMapper.transformModelToEntity(user))
        }
    }

    override fun logoutUser(): Observable<Boolean> {
        return Observable.defer {
            contentClient.deleteIdInSharedPrefs()
        }
    }
}