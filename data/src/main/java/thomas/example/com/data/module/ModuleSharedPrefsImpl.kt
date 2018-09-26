package thomas.example.com.data.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleSharedPrefsImpl @Inject constructor(context: Context) : ModuleSharedPrefs {

    companion object {
        const val CURRENT_USER_ID: String = "CURRENT_USER_ID"
        const val CURRENT_INSTRUMENT_MODE: String = "GUITAR_MODE"

        const val INSTRUMENT_MODE_GUITAR = "GUITAR_MODE"
        const val INSTRUMENT_MODE_BASS = "BASS_MODE"
    }

    private var sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setIdUserInSharedPrefs(idUser: String) {
        sharedPreferences.edit().putString(CURRENT_USER_ID, idUser).apply()
    }

    override fun getIdUserInSharedPrefs(): String {
        return sharedPreferences.getString(CURRENT_USER_ID, null)
    }

    override fun deleteIdUserInSharedPrefs() {
        return sharedPreferences.edit().remove(CURRENT_USER_ID).apply()
    }

    override fun setInstrumentModeInSharedPrefs() {
        if (sharedPreferences.getString(CURRENT_INSTRUMENT_MODE, INSTRUMENT_MODE_GUITAR) == INSTRUMENT_MODE_GUITAR) {
            sharedPreferences.edit().putString(CURRENT_INSTRUMENT_MODE, INSTRUMENT_MODE_BASS).apply()
        } else {
            sharedPreferences.edit().putString(CURRENT_INSTRUMENT_MODE, INSTRUMENT_MODE_GUITAR).apply()
        }
    }
}