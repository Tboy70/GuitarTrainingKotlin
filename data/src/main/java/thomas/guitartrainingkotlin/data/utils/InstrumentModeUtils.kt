package thomas.guitartrainingkotlin.data.utils

import thomas.guitartrainingkotlin.data.manager.SharedPrefsManagerImpl

object InstrumentModeUtils {

    fun getRemoteValueFromInstrumentMode(instrumentMode: String): Int {
        return when (instrumentMode) {
            SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR -> 1
            SharedPrefsManagerImpl.INSTRUMENT_MODE_BASS -> 2
            else -> 1
        }
    }

    fun getInstrumentModeFromRemoteValue(instrumentMode: Int): String {
        return when (instrumentMode) {
            1 -> SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR
            2 -> SharedPrefsManagerImpl.INSTRUMENT_MODE_BASS
            else -> SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR
        }
    }
}