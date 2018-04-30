package thomas.example.com.data.executor

import thomas.example.com.executor.ThreadExecutor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class JobExecutor @Inject constructor() : ThreadExecutor {

    private val threadPoolExecutor: ThreadPoolExecutor

    companion object {
        const val INITIAL_POOL_SIZE = 3
        const val MAX_POOL_SIZE = 5

        // Sets the amount of time an idle thread waits before terminating
        const val KEEP_ALIVE_TIME = 10

        // Sets the Time Unit to seconds
        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }

    override fun execute(command: Runnable) {
        this.threadPoolExecutor.execute(command)
    }

    init {
        val workQueue = LinkedBlockingQueue<Runnable>()
        this.threadPoolExecutor = ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME.toLong(), KEEP_ALIVE_TIME_UNIT, workQueue)
    }
}