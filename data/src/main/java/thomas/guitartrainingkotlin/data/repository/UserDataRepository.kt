package thomas.guitartrainingkotlin.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import thomas.guitartrainingkotlin.data.business.APIBusinessHelper
import thomas.guitartrainingkotlin.data.business.ContentBusinessHelper
import thomas.guitartrainingkotlin.data.mapper.UserEntityDataMapper
import thomas.guitartrainingkotlin.domain.model.User
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@FlowPreview
@ExperimentalCoroutinesApi
@Singleton
class UserDataRepository @Inject constructor(
    private val userEntityDataMapper: UserEntityDataMapper,
    private val contentBusinessHelper: ContentBusinessHelper,
    private val apiBusinessHelper: APIBusinessHelper
) : UserRepository {

    /**
     * No need of try / catch anymore !
     */
    override fun retrieveUserIdInSharedPrefs(): Flow<String?> {
        return contentBusinessHelper.getUserIdInSharedPrefs()
    }

    override fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Flow<Int> {
        return contentBusinessHelper.setInstrumentModeInSharedPrefs(instrumentMode)
    }

    override fun retrieveInstrumentModeInSharedPrefs(): Flow<Int> {
        return contentBusinessHelper.retrieveInstrumentModeInSharedPrefs()
    }

    override suspend fun connectUser(user: User): Flow<User> {
        return apiBusinessHelper.connectUser(userEntityDataMapper.transformToEntity(user)).onEach {
            it.userId?.let { userId ->
                contentBusinessHelper.setIdInSharedPrefs(userId)
            }
        }.map {
            userEntityDataMapper.transformFromEntity(it)
        }
    }

    override fun createNewUser(user: User): Flow<Unit> {
        return apiBusinessHelper.createNewUser(userEntityDataMapper.transformToEntity(user))

    }

    override fun retrieveUserById(userId: String): Flow<User> {
        return apiBusinessHelper.retrieveUserById(userId).map {
            userEntityDataMapper.transformFromEntity(it)
        }.onCompletion { cause ->
            if (cause != null) {
                /** Replace doOnError ?*/
                contentBusinessHelper.deleteIdInSharedPrefs()
            }
        }
    }

    override fun retrievePassword(emailAddress: String): Flow<Unit> {
        return apiBusinessHelper.retrievePassword(emailAddress)
    }


    override fun logoutUser(): Flow<Unit> {
        return contentBusinessHelper.deleteIdInSharedPrefs()
    }

    override fun suppressAccount(userId: String): Flow<Unit> {
        return apiBusinessHelper.suppressAccount(userId).onEach {
            contentBusinessHelper.deleteIdInSharedPrefs()
            contentBusinessHelper.deleteInstrumentModeInSharedPrefs()
        }
    }
}