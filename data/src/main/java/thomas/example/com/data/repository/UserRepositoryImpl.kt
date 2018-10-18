package thomas.example.com.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import thomas.example.com.data.mapper.UserEntityDataMapper
import thomas.example.com.data.repository.client.APIClient
import thomas.example.com.data.repository.client.ContentClient
import thomas.example.com.model.User
import thomas.example.com.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userEntityDataMapper: UserEntityDataMapper,
    private val contentClient: ContentClient,
    private val apiClient: APIClient
) : UserRepository {

    /**
     * No need of try / catch anymore !
     */
    override fun getIdUserInSharedPrefs(): Single<String> {
        return Single.defer { contentClient.getIdInSharedPrefs() }
    }

    override fun setIdUserInSharedPrefs(idUser: String?): Observable<Boolean> {
        return Observable.defer { Observable.just(true) }
    }

    override fun setInstrumentModeInSharedPrefs(): Observable<Boolean> {
        return Observable.defer {
            contentClient.setInstrumentModeInSharedPrefs()
        }
    }

    override fun connectUser(user: User): Single<User> {
        return Single.defer {
            apiClient.connectUser(userEntityDataMapper.transformModelToEntity(user))?.map {
                userEntityDataMapper.transformEntityToModel(it)
            }?.doOnSuccess {
                contentClient.setIdInSharedPrefs(it.idUser)
            }
        }
    }

    override fun retrieveUserById(idUser: String): Single<User> {
        return Single.defer {
            apiClient.retrieveUserById(idUser).map {
                userEntityDataMapper.transformEntityToModel(it)
            }
        }
    }

    override fun createNewUser(user: User): Completable {
        return Completable.defer {
            apiClient.createNewUser(userEntityDataMapper.transformModelToEntity(user))
        }
    }

    override fun suppressAccount(idUser: String): Observable<Boolean> {
        return Observable.defer {
            apiClient.suppressAccount(idUser).doOnNext {
                contentClient.deleteIdInSharedPrefs()
            }
        }
    }

    override fun logoutUser(): Completable {
        return Completable.defer {
            contentClient.deleteIdInSharedPrefs()
        }
    }
}