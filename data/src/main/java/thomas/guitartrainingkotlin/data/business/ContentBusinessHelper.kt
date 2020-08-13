package thomas.guitartrainingkotlin.data.business

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.data.manager.sharedprefs.SharedPrefsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentBusinessHelper @Inject constructor(private val sharedPrefsManager: SharedPrefsManager) {

    fun getUserIdInSharedPrefs(): Flow<String?> {
        return sharedPrefsManager.getUserIdInSharedPrefs()
    }

    fun setIdInSharedPrefs(userId: String) {
        sharedPrefsManager.setUserIdInSharedPrefs(userId)
    }

    fun deleteIdInSharedPrefs() : Flow<Unit> {
        return sharedPrefsManager.deleteUserIdInSharedPrefs()
    }

    fun retrieveInstrumentModeInSharedPrefs(): Flow<Int> {
        return sharedPrefsManager.getInstrumentModeInSharedPrefs()
    }

    fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Flow<Int> {
        return sharedPrefsManager.setInstrumentModeInSharedPrefs(instrumentMode)
    }

    fun deleteInstrumentModeInSharedPrefs() {
        sharedPrefsManager.deleteCurrentInstrumentInSharedPrefs()
    }
}