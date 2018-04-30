package thomas.example.com.guitarTrainingKotlin.executor

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import thomas.example.com.executor.PostExecutionThread
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UIThread @Inject constructor() : PostExecutionThread {

    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}