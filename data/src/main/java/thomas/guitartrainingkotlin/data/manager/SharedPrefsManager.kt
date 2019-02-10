package thomas.guitartrainingkotlin.data.manager

interface SharedPrefsManager {

    // Shared prefs
    fun getUserIdInSharedPrefs(): String?

    fun setUserIdInSharedPrefs(userId: String)

    fun deleteUserIdInSharedPrefs()

    fun getInstrumentModeInSharedPrefs(): Int

    fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Int

    fun deleteCurrentInstrumentInSharedPrefs()
}