package thomas.guitartrainingkotlin.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import thomas.guitartrainingkotlin.data.business.APIBusinessHelper
import thomas.guitartrainingkotlin.data.business.ContentBusinessHelper
import thomas.guitartrainingkotlin.data.mapper.UserEntityDataMapper
import thomas.guitartrainingkotlin.domain.model.User
import thomas.guitartrainingkotlin.domain.repository.UserRepository
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
    override fun retrieveUserIdInSharedPrefs(): Single<String> {
        return Single.defer {
            Single.just(contentBusinessHelper.getUserIdInSharedPrefs())
        }
    }

    override fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Single<Int> {
        return Single.defer {
           Single.just(contentBusinessHelper.setInstrumentModeInSharedPrefs(instrumentMode))
        }
    }

    override fun retrieveInstrumentModeInSharedPrefs(): Single<Int> {
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