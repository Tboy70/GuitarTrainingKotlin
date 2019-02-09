package thomas.guitartrainingkotlin.data.manager

interface SharedPrefsManager {

    // Shared prefs
    fun getUserIdInSharedPrefs(): String

    fun setUserIdInSharedPrefs(userId: String)

    fun deleteUserIdInSharedPrefs()

    fun getInstrumentModeInSharedPrefs(): String

    fun setInstrumentModeInSharedPrefs(instrumentMode: String): String

    fun deleteCurrentInstrumentInSharedPrefs()
}