package thomas.guitartrainingkotlin.presentation.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateTimeUtils {

    const val DEFAULT_DURATION_LEFT: Long = -1
    const val SECONDS_IN_ONE_MINUTE: Long = 60
    const val MINUTE_TO_MILLISECONDS: Long = 1000
    const val FROM_API_FORMAT = "yyyy-MM-dd"
    const val WANTED_FORMAT = "dd/MM/yyyy"
    const val WANTED_FORMAT_WITHOUT_YEAR = "dd/MM"
    private const val CONVERSION_FORMAT = "%02d:%02d:%02d"

    fun convertMillisecondsToTimeFormat(milliSeconds: Long): String {
        return String.format(
            Locale.getDefault(), CONVERSION_FORMAT,
            TimeUnit.MILLISECONDS.toHours(milliSeconds),
            TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(
                    milliSeconds
                )
            ),
            TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    milliSeconds
                )
            )
        )
    }

    fun formatDate(baseFormat: String, wantedFormat: String, dateToFormat: String): String {
        return SimpleDateFormat(baseFormat, Locale.FRENCH).parse(dateToFormat)?.let {
            SimpleDateFormat(wantedFormat, Locale.FRENCH).format(it)
        } ?: SimpleDateFormat(wantedFormat, Locale.FRENCH).format(Date())
    }

}