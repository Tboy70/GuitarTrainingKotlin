package thomas.example.com.guitarTrainingKotlin.utils

import java.util.*
import java.util.concurrent.TimeUnit

class DateTimeUtils {

    companion object {
        const val DEFAULT_DURATION_LEFT: Long = -1
        const val SECONDS_IN_ONE_MINUTE: Long = 60
        const val MINUTE_TO_MILLISECONDS: Long = 1000
        private const val CONVERSION_FORMAT = "%02d:%02d:%02d"

        fun convertMillisecondsToTimeFormat(milliSeconds: Long): String {
            return String.format(Locale.getDefault(), CONVERSION_FORMAT,
                    TimeUnit.MILLISECONDS.toHours(milliSeconds),
                    TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                    TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)))
        }
    }
}