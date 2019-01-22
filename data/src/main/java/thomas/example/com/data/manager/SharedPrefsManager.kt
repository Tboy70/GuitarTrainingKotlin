package thomas.example.com.data.manager

interface SharedPrefsManager {

    // Shared prefs
    fun getUserIdInSharedPrefs(): String
    fun setIdUserInSharedPrefs(idUser: String?)

    fun deleteIdUserInSharedPrefs()

    fun setInstrumentModeInSharedPrefs()

    fun getInstrumentModeInSharedPrefs(): String
}