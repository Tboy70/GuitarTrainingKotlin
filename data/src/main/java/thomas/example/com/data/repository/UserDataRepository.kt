package thomas.example.com.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import thomas.example.com.data.mapper.UserEntityDataMapper
import thomas.example.com.data.business.APIBusinessHelper
import thomas.example.com.data.business.ContentBusinessHelper
import thomas.example.com.model.User
import thomas.example.com.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepository @Inject constructor(
    private val userEntityDataMapper: UserEntityDataMapper,
    private val contentBusinessHelper: ContentBusinessHelper,
    private val apiBusinessHelper: APIBusinessHelper
) : UserRepository {

    /**
     * No need of try / catch anymore !
     */
    override fun getUserIdInSharedPrefs(): Single<String> {
        return Single.defer { contentBusinessHelper.getUserIdInSharedPrefs() }
    }

    override fun setUserIdInSharedPrefs(userId: String?): Observable<Boolean> {
        return Observable.defer { Observable.just(true) }
    }

    override fun setInstrumentModeInSharedPrefs(): Completable {
        return Completable.defer {
            contentBusinessHelper.setInstrumentModeInSharedPrefs()
        }
    }

    override fun connectUser(user: User): Single<User> {
        return Single.defer {
            apiBusinessHelper.connectUser(userEntityDataMapper.transformModelToEntity(user))?.map {
                userEntityDataMapper.transformEntityToModel(it)
            }?.doOnSuccess {
                contentBusinessHelper.setIdInSharedPrefs(it.idUser)
            }
        }
    }

    override fun retrieveUserById(idUser: String): Single<User> {
        return Single.defer {
            apiBusinessHelper.retrieveUserById(idUser).map {
                userEntityDataMapper.transformEntityToModel(it)
            }
        }
    }

    override fun createNewUser(user: User): Completable {
        return Completable.defer {
            apiBusinessHelper.createNewUser(userEntityDataMapper.transformModelToEntity(user))
        }
    }

    override fun suppressAccount(idUser: String): Completable {
        return Completable.defer {
            apiBusinessHelper.suppressAccount(idUser).doOnComplete {
                contentBusinessHelper.deleteIdInSharedPrefs()
            }
        }
    }

    override fun logoutUser(): Completable {
        return Completable.defer {
            contentBusinessHelper.deleteIdInSharedPrefs()
        }
    }
}