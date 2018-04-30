package thomas.example.com.executor

import rx.Scheduler

/**
 * Interface implemented by UIThread class.
 */
interface PostExecutionThread {
    fun getScheduler() : Scheduler
}