package thomas.guitartrainingkotlin.data.manager.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsManagerImpl @Inject constructor(context: Context) :
    SharedPrefsManager {

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getUserIdInSharedPrefs(): String? {
        return sharedPreferences.getString(CURRENT_USER_ID, null)
    }

    override fun setUserIdInSharedPrefs(userId: String) {
        sharedPreferences.edit().putString(CURRENT_USER_ID, userId).apply()
    }

    override fun deleteUserIdInSharedPrefs() {
        sharedPreferences.edit().remove(CURRENT_USER_ID).apply()
    }

    override fun getInstrumentModeInSharedPrefs(): Int {
        return sharedPreferences.getInt(
            CURRENT_INSTRUMENT_MODE,
            INSTRUMENT_MODE_GUITAR
        )
    }

    override fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Int {
        var newInstrumentMode = instrumentMode
        if (instrumentMode == INSTRUMENT_MODE_GUITAR) {
            sharedPreferences.edit().putInt(
                CURRENT_INSTRUMENT_MODE,
                INSTRUMENT_MODE_BASS
            ).apply()
            newInstrumentMode =
                INSTRUMENT_MODE_BASS
        } else if (instrumentMode == INSTRUMENT_MODE_BASS) {
            sharedPreferences.edit().putInt(
                CURRENT_INSTRUMENT_MODE,
                INSTRUMENT_MODE_GUITAR
            ).apply()
            newInstrumentMode =
                INSTRUMENT_MODE_GUITAR
        }
        return newInstrumentMode
    }

    override fun deleteCurrentInstrumentInSharedPrefs() {
        sharedPreferences.edit().remove(CURRENT_INSTRUMENT_MODE).apply()
    }

    companion object {
        const val INSTRUMENT_MODE_BASS = InstrumentModeValues.INSTRUMENT_MODE_BASS
        const val INSTRUMENT_MODE_GUITAR = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
        const val CURRENT_INSTRUMENT_MODE = "CURRENT_INSTRUMENT_MODE"
        const val CURRENT_USER_ID: String = "CURRENT_USER_ID"
    }
}