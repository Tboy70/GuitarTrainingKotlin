package thomas.example.com.data.utils

import thomas.example.com.data.manager.SharedPrefsManagerImpl

object InstrumentModeUtils {

    fun getIntValueFromInstrumentMode(instrumentMode: String): Int {
        return when (instrumentMode) {
            SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR -> 1
            SharedPrefsManagerImpl.INSTRUMENT_MODE_BASS -> 2
            else -> 1
        }
    }
}