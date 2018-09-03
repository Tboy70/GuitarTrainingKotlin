package thomas.example.com.guitarTrainingKotlin.fragment.ui

import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.timer_dialog_layout.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.component.listener.OnTimerDialogDismiss
import thomas.example.com.guitarTrainingKotlin.utils.DateTimeUtils
import thomas.example.com.utils.ConstantTags

class TimerDialogFragment : DialogFragment() {

    enum class TimerStatus {
        STARTED,
        STOPPED
    }

    private lateinit var dialogTitle: String
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var timerDialogDismissListener: OnTimerDialogDismiss

    private var durationExercise: Long = 0
    private var timeCountInMilliSeconds: Long = 0
    private var timeLeftToTheEndOfTimer: Long = 0
    private var timerStatus = TimerStatus.STOPPED

    companion object {

        private const val DIALOG_FRAGMENT_TITLE = "thomas.example.com.guitarTrainingKotlin.fragment.ui.TimerDialogFragment.DIALOG_FRAGMENT_TITLE"
        private const val DIALOG_FRAGMENT_DURATION_EXERCISE = "thomas.example.com.guitarTrainingKotlin.fragment.ui.TimerDialogFragment.DIALOG_FRAGMENT_DURATION_EXERCISE"

        fun newInstance(dialogTitle: String, durationExercise: Long): TimerDialogFragment {

            val args = Bundle()

            args.putString(DIALOG_FRAGMENT_TITLE, dialogTitle)
            args.putLong(DIALOG_FRAGMENT_DURATION_EXERCISE, durationExercise)

            val fragment = TimerDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.timer_dialog_layout, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args != null) {
            dialogTitle = args.getString(DIALOG_FRAGMENT_TITLE)
            durationExercise = args.getLong(DIALOG_FRAGMENT_DURATION_EXERCISE)
            timeLeftToTheEndOfTimer = durationExercise
        }

        text_view_timer.text = DateTimeUtils.convertMillisecondsToTimeFormat(durationExercise)

        dialog.setCanceledOnTouchOutside(true)
        dialog.setTitle(dialogTitle)

        handleClickButtonStartTimer()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        try {
            countDownTimer.cancel()
        } catch (e: UninitializedPropertyAccessException) {
            Log.e(ConstantTags.TIME_DIALOG_FRAGMENT.tag, activity.getString(R.string.error_count_down_not_initialized))
        }
        timerDialogDismissListener.onDismiss(timeLeftToTheEndOfTimer)
    }

    fun setTimerDialogDismissListener(onTimerDialogDismiss: OnTimerDialogDismiss) {
        this.timerDialogDismissListener = onTimerDialogDismiss
    }

    private fun handleClickButtonStartTimer() {
        button_start_timer.setOnClickListener {
            startAndPauseTimer()
        }
    }

    private fun startAndPauseTimer() {
        if (timerStatus == TimerStatus.STOPPED) {
            initializeTimeValue(durationExercise)
            initializeProgressBar()
            timerStatus = TimerStatus.STARTED
            button_start_timer.text = getString(R.string.timer_pause_button)
            startTimer()
        } else {
            timerStatus = TimerStatus.STOPPED
            button_start_timer.text = getString(R.string.timer_start_button)
            pauseTimer()
        }
    }

    private fun initializeTimeValue(durationExercise: Long) {
        timeCountInMilliSeconds = durationExercise
    }

    private fun initializeProgressBar() {
        progress_bar_timer.max = (timeCountInMilliSeconds / DateTimeUtils.MINUTE_TO_MILLISECONDS).toInt()
        progress_bar_timer.progress = (timeCountInMilliSeconds / DateTimeUtils.MINUTE_TO_MILLISECONDS).toInt()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeCountInMilliSeconds, DateTimeUtils.MINUTE_TO_MILLISECONDS) {
            override fun onTick(millisUntilFinished: Long) {
                text_view_timer.text = DateTimeUtils.convertMillisecondsToTimeFormat(millisUntilFinished)
                progress_bar_timer.progress = (millisUntilFinished / DateTimeUtils.MINUTE_TO_MILLISECONDS).toInt()
                timeLeftToTheEndOfTimer = millisUntilFinished
            }

            override fun onFinish() {
                text_view_timer.text = DateTimeUtils.convertMillisecondsToTimeFormat(timeCountInMilliSeconds)
                initializeProgressBar()
                timerStatus = TimerStatus.STOPPED
            }

        }.start()
        countDownTimer.start()
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
    }
}