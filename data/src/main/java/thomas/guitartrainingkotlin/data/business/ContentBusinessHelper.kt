package thomas.guitartrainingkotlin.data.business

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import thomas.guitartrainingkotlin.data.manager.db.DBManager
import thomas.guitartrainingkotlin.data.manager.sharedprefs.SharedPrefsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentBusinessHelper @Inject constructor(
    private val dbManager: DBManager,
    private val sharedPrefsManager: SharedPrefsManager
) {

    fun deleteDatabase() : Flow<Unit> {
        return flowOf(dbManager.clearDatabase())
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