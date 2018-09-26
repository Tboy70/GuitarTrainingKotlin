package thomas.example.com.data.utils

import thomas.example.com.data.module.ModuleSharedPrefsImpl

class InstrumentModeUtils {

    companion object {

        fun getIntValueFromInstrumentMode(instrumentMode: String): Int {
            return when (instrumentMode) {
                ModuleSharedPrefsImpl.INSTRUMENT_MODE_GUITAR -> 1
                ModuleSharedPrefsImpl.INSTRUMENT_MODE_BASS -> 2
                else -> 1
            }
        }
    }
}