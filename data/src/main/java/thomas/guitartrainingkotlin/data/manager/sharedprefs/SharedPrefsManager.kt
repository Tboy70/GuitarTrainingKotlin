package thomas.guitartrainingkotlin.data.manager.sharedprefs

import kotlinx.coroutines.flow.Flow

interface SharedPrefsManager {

    // Shared prefs
    fun getUserIdInSharedPrefs(): Flow<String?>

    fun setUserIdInSharedPrefs(userId: String)

    fun deleteUserIdInSharedPrefs() : Flow<Unit>

    fun getInstrumentModeInSharedPrefs(): Flow<Int>

    fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Flow<Int>

    fun deleteCurrentInstrumentInSharedPrefs()
}