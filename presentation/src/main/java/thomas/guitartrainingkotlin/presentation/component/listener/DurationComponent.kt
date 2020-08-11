package thomas.guitartrainingkotlin.presentation.component.listener

import android.widget.TextView

interface DurationComponent {

    fun setDuration(
        durationExercise: Int, durationLeft: Long, exerciseDuration: TextView,
        durationText: String?, exerciseDurationLeft: TextView, durationTextLeft: String?
    ): Long

    fun setDurationLeft(
        exerciseScaleDurationLeft: TextView,
        durationTextLeft: String,
        timeCountInMilliSeconds: Long
    ): Long
}