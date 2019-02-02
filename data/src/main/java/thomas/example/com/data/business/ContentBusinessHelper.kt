package thomas.example.com.data.business

import thomas.example.com.data.manager.SharedPrefsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentBusinessHelper @Inject constructor(private val sharedPrefsManager: SharedPrefsManager) {

    fun getUserIdInSharedPrefs(): String {
        return sharedPrefsManager.getUserIdInSharedPrefs()
    }

    fun setIdInSharedPrefs(userId: String) {
        sharedPrefsManager.setUserIdInSharedPrefs(userId)
    }

    fun deleteIdInSharedPrefs() {
        sharedPrefsManager.deleteUserIdInSharedPrefs()
    }

    fun retrieveInstrumentModeInSharedPrefs(): String {
        return sharedPrefsManager.getInstrumentModeInSharedPrefs()
    }

    fun setInstrumentModeInSharedPrefs(instrumentMode: String): String {
        return sharedPrefsManager.setInstrumentModeInSharedPrefs(instrumentMode)
    }

    fun deleteInstrumentModeInSharedPrefs() {
        sharedPrefsManager.deleteCurrentInstrumentInSharedPrefs()
    }
}