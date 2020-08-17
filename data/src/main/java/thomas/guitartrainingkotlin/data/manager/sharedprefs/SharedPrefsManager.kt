package thomas.guitartrainingkotlin.data.manager.sharedprefs

import kotlinx.coroutines.flow.Flow

interface SharedPrefsManager {

    fun getInstrumentModeInSharedPrefs(): Flow<Int>

    fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Flow<Int>

    fun deleteCurrentInstrumentInSharedPrefs()
}