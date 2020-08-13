package thomas.guitartrainingkotlin.domain.repository

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.User

interface UserRepository {

    // Shared prefs
    fun retrieveUserIdInSharedPrefs(): Flow<String?>

    fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Flow<Int>

    fun retrieveInstrumentModeInSharedPrefs(): Flow<Int>

    // User
    suspend fun connectUser(user: User): Flow<User>

    fun createNewUser(user: User) : Flow<Unit>

    fun retrieveUserById(userId: String): Flow<User>

    fun retrievePassword(emailAddress: String): Flow<Unit>

    fun logoutUser(): Flow<Unit>

    fun suppressAccount(userId: String): Flow<Unit>

}
