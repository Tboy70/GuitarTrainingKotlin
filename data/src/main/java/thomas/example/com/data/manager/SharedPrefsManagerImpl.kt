package thomas.example.com.data.manager

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsManagerImpl @Inject constructor(context: Context) : SharedPrefsManager {

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getUserIdInSharedPrefs(): String {
        return sharedPreferences.getString(CURRENT_USER_ID, null)
    }

    override fun setIdUserInSharedPrefs(idUser: String) {
        sharedPreferences.edit().putString(CURRENT_USER_ID, idUser).apply()
    }

    override fun deleteIdUserInSharedPrefs() {
        sharedPreferences.edit().remove(CURRENT_USER_ID).apply()
    }

    override fun setInstrumentModeInSharedPrefs() {
        if (sharedPreferences.getString(CURRENT_INSTRUMENT_MODE, INSTRUMENT_MODE_GUITAR) == INSTRUMENT_MODE_GUITAR) {
            sharedPreferences.edit().putString(CURRENT_INSTRUMENT_MODE, INSTRUMENT_MODE_BASS).apply()
        } else {
            sharedPreferences.edit().putString(CURRENT_INSTRUMENT_MODE, INSTRUMENT_MODE_GUITAR).apply()
        }
    }

    override fun getInstrumentModeInSharedPrefs(): String {
        return sharedPreferences.getString(CURRENT_INSTRUMENT_MODE, INSTRUMENT_MODE_GUITAR)
    }

    companion object {
        const val INSTRUMENT_MODE_BASS = "BASS_MODE"
        const val INSTRUMENT_MODE_GUITAR = "GUITAR_MODE"
        const val CURRENT_USER_ID: String = "CURRENT_USER_ID"
        const val CURRENT_INSTRUMENT_MODE: String = "GUITAR_MODE"
    }
}