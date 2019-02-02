package thomas.example.com.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import thomas.example.com.data.business.APIBusinessHelper
import thomas.example.com.data.business.ContentBusinessHelper
import thomas.example.com.data.mapper.UserEntityDataMapper
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
        return Single.defer {
            Single.just(contentBusinessHelper.getUserIdInSharedPrefs())
        }
    }

    override fun setInstrumentModeInSharedPrefs(instrumentMode: String): Single<String> {
        return Single.defer {
           Single.just(contentBusinessHelper.setInstrumentModeInSharedPrefs(instrumentMode))
        }
    }

    override fun retrieveInstrumentModeInSharedPrefs(): Single<String> {
        return Single.defer {
            Single.just(contentBusinessHelper.retrieveInstrumentModeInSharedPrefs())
        }
    }

    override fun connectUser(user: User): Single<User> {
        return Single.defer {
            apiBusinessHelper.connectUser(userEntityDataMapper.transformToEntity(user)).map {
                userEntityDataMapper.transformFromEntity(it)
            }?.doOnSuccess {
                it.userId?.let { userId ->
                    contentBusinessHelper.setIdInSharedPrefs(userId)
                }
            }
        }
    }

    override fun createNewUser(user: User): Completable {
        return Completable.defer {
            apiBusinessHelper.createNewUser(userEntityDataMapper.transformToEntity(user))
        }
    }

    override fun retrieveUserById(userId: String): Single<User> {
        return Single.defer {
            apiBusinessHelper.retrieveUserById(userId).map {
                userEntityDataMapper.transformFromEntity(it)
            }.doOnError {
                contentBusinessHelper.deleteIdInSharedPrefs()
            }
        }
    }

    override fun logoutUser(): Completable {
        return Completable.defer {
            contentBusinessHelper.deleteIdInSharedPrefs()
            Completable.complete()
        }
    }

    override fun suppressAccount(userId: String): Completable {
        return Completable.defer {
            apiBusinessHelper.suppressAccount(userId).doOnComplete {
                contentBusinessHelper.deleteIdInSharedPrefs()
                contentBusinessHelper.deleteInstrumentModeInSharedPrefs()
            }
        }
    }
}