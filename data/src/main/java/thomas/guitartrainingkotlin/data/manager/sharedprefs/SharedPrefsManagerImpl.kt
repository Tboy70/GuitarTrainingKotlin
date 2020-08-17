package thomas.guitartrainingkotlin.data.manager.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsManagerImpl @Inject constructor(context: Context) : SharedPrefsManager {

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    override fun getInstrumentModeInSharedPrefs(): Flow<Int> =
        flow {
            emit(
                sharedPreferences.getInt(
                    CURRENT_INSTRUMENT_MODE,
                    InstrumentModeValues.INSTRUMENT_MODE_GUITAR
                )
            )
        }

    override fun setInstrumentModeInSharedPrefs(instrumentMode: Int): Flow<Int> {
        var newInstrumentMode = instrumentMode
        if (instrumentMode == InstrumentModeValues.INSTRUMENT_MODE_GUITAR) {
            sharedPreferences.edit().putInt(
                CURRENT_INSTRUMENT_MODE,
                InstrumentModeValues.INSTRUMENT_MODE_BASS
            ).apply()
            newInstrumentMode = InstrumentModeValues.INSTRUMENT_MODE_BASS
        } else if (instrumentMode == InstrumentModeValues.INSTRUMENT_MODE_BASS) {
            sharedPreferences.edit().putInt(
                CURRENT_INSTRUMENT_MODE,
                InstrumentModeValues.INSTRUMENT_MODE_GUITAR
            ).apply()
            newInstrumentMode = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
        }
        return flow { emit(newInstrumentMode) }
    }

    override fun deleteCurrentInstrumentInSharedPrefs() {
        sharedPreferences.edit().remove(CURRENT_INSTRUMENT_MODE).apply()
    }

    companion object {
        const val CURRENT_INSTRUMENT_MODE = "CURRENT_INSTRUMENT_MODE"
    }
}