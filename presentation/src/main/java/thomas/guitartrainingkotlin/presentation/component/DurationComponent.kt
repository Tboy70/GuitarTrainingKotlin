package thomas.guitartrainingkotlin.presentation.component

import android.os.Build
import android.text.Html
import android.widget.TextView
import thomas.guitartrainingkotlin.presentation.di.annotation.PerActivity
import thomas.guitartrainingkotlin.presentation.utils.DateTimeUtils
import java.util.*
import javax.inject.Inject

@PerActivity
class DurationComponent @Inject constructor() {

    @Suppress("DEPRECATION")
    fun setDuration(
        durationExercise: Int, durationLeft: Long, exerciseDuration: TextView,
        durationText: String?, exerciseDurationLeft: TextView, durationTextLeft: String?
    ): Long {

        var mDurationLeft = durationLeft

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            exerciseDuration.text = Html.fromHtml(
                String.format(
                    Locale.FRANCE,
                    durationText.toString(),
                    durationExercise.toString()
                ), Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            exerciseDuration.text = Html.fromHtml(
                String.format(
                    Locale.FRANCE,
                    durationText.toString(),
                    durationExercise.toString()
                )
            )
        }

        if (durationLeft == DateTimeUtils.DEFAULT_DURATION_LEFT) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                exerciseDurationLeft.text = Html.fromHtml(
                    String.format(
                        durationTextLeft.toString(),
                        durationExercise.toString()
                    ), Html.FROM_HTML_MODE_COMPACT
                )
                mDurationLeft = durationExercise * DateTimeUtils.SECONDS_IN_ONE_MINUTE *
                        DateTimeUtils.MINUTE_TO_MILLISECONDS
            } else {
                exerciseDurationLeft.text = Html.fromHtml(
                    String.format(
                        durationTextLeft.toString(),
                        durationExercise.toString()
                    )
                )
                mDurationLeft = durationExercise * DateTimeUtils.SECONDS_IN_ONE_MINUTE *
                        DateTimeUtils.MINUTE_TO_MILLISECONDS
            }

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                exerciseDurationLeft.text = Html.fromHtml(
                    String.format(
                        durationTextLeft.toString(),
                        DateTimeUtils.convertMillisecondsToTimeFormat(durationLeft)
                    ), Html.FROM_HTML_MODE_COMPACT
                )
            } else {
                exerciseDurationLeft.text = Html.fromHtml(
                    String.format(
                        durationTextLeft.toString(),
                        DateTimeUtils.convertMillisecondsToTimeFormat(durationLeft)
                    )
                )
            }
        }

        return mDurationLeft
    }

    @Suppress("DEPRECATION")
    fun setDurationLeft(
        exerciseScaleDurationLeft: TextView,
        durationTextLeft: String,
        timeCountInMilliSeconds: Long
    ): Long {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            exerciseScaleDurationLeft.text = Html.fromHtml(
                String.format(
                    durationTextLeft,
                    DateTimeUtils.convertMillisecondsToTimeFormat(timeCountInMilliSeconds)
                ), Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            exerciseScaleDurationLeft.text = Html.fromHtml(
                String.format(
                    durationTextLeft,
                    DateTimeUtils.convertMillisecondsToTimeFormat(timeCountInMilliSeconds)
                )
            )
        }
        return timeCountInMilliSeconds
    }
}