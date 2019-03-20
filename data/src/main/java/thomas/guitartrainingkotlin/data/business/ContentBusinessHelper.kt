package thomas.guitartrainingkotlin.data.business

import thomas.guitartrainingkotlin.data.manager.sharedprefs.SharedPrefsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentBusinessHelper @Inject constructor(private val sharedPrefsManager: SharedPrefsManager) {

    fun getUserIdInSharedPrefs(): String? {
        return sharedPrefsManager.getUserIdInSharedPrefs()
    }

    fun setIdInSharedPrefs(userId: String) {
        sharedPrefsManager.setUserIdInSharedPrefs(userId)
    }

    fun deleteIdInSharedPrefs() {
        sharedPrefsManager.deleteUserIdInSharedPrefs()
    }

    fun retrieveInstrumentModeInSharedPrefs(): Int {
        return sharedPrefsManager.getInstrumentModeInSharedPrefs()
    }

    fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Int {
        return sharedPrefsManager.setInstrumentModeInSharedPrefs(instrumentMode)
    }

    fun deleteInstrumentModeInSharedPrefs() {
        sharedPrefsManager.deleteCurrentInstrumentInSharedPrefs()
    }
}