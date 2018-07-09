package thomas.example.com.guitarTrainingKotlin.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import thomas.example.com.executor.PostExecutionThread
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UIThread @Inject constructor() : PostExecutionThread {
    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

}