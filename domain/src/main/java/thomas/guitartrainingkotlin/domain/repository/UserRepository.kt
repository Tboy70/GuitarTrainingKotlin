package thomas.guitartrainingkotlin.domain.repository

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.User

interface UserRepository {

    // Shared prefs

    fun retrieveInstrumentModeInSharedPrefs(): Flow<Int>

    fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Flow<Int>

    // User
    fun connectUser(user: User): Flow<User>

    suspend fun retrieveUserId(): Flow<String?>

    fun createNewUser(user: User): Flow<Unit>

    fun retrieveUserById(userId: String): Flow<User>

    fun retrievePassword(emailAddress: String): Flow<Unit>

    suspend fun logoutUser(): Flow<Unit>

    fun suppressAccount(userId: String): Flow<Unit>

}
