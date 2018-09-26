package thomas.example.com.data.module

interface ModuleSharedPrefs {

    fun setIdUserInSharedPrefs(idUser: String)

    fun getIdUserInSharedPrefs(): String

    fun deleteIdUserInSharedPrefs()

    fun setInstrumentModeInSharedPrefs()

    fun getInstrumentModeInSharedPrefs(): String
}