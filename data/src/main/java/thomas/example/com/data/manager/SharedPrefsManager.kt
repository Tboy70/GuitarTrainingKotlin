package thomas.example.com.data.manager

interface SharedPrefsManager {

    // Shared prefs
    fun getUserIdInSharedPrefs(): String
    fun setUserIdInSharedPrefs(userId: String)
    fun deleteUserIdInSharedPrefs()

    fun setInstrumentModeInSharedPrefs(instrumentMode: String)

    fun getInstrumentModeInSharedPrefs(): String
}