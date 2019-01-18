package thomas.example.com.data.manager

interface SharedPrefsManager {

    fun setIdUserInSharedPrefs(idUser: String?)

    fun getUserIdInSharedPrefs(): String

    fun deleteIdUserInSharedPrefs()

    fun setInstrumentModeInSharedPrefs()

    fun getInstrumentModeInSharedPrefs(): String
}