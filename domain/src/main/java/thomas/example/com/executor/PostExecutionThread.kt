package thomas.example.com.executor

import rx.Scheduler

interface PostExecutionThread {

    fun getScheduler() : Scheduler
}