package thomas.example.com.executor

import io.reactivex.Scheduler

interface PostExecutionThread {

    fun getScheduler() : Scheduler
}