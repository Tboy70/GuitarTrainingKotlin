package thomas.example.com.guitarTrainingKotlin.presenter

import android.util.Log
import rx.Subscriber
import thomas.example.com.executor.PostExecutionThread
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.guitarTrainingKotlin.activity.listener.BaseNavigatorListener
import thomas.example.com.guitarTrainingKotlin.activity.listener.StartNavigatorListener
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.interactor.GetIdInSharedPrefs
import javax.inject.Inject

@PerActivity
class StartPresenter @Inject constructor(baseNavigatorListener: BaseNavigatorListener,
                                         private var getIdInSharedPrefs: GetIdInSharedPrefs,
                                         threadExecutor: ThreadExecutor,
                                         postExecutionThread: PostExecutionThread) {

    private lateinit var startNavigatorListener: StartNavigatorListener

    init {
        if (baseNavigatorListener is StartNavigatorListener) {
            startNavigatorListener = baseNavigatorListener
        }
    }

    fun getUserPrefIsConnected() {
        getIdInSharedPrefs.execute(GetSharedPrefsId(), null)
    }

    private inner class GetSharedPrefsId : Subscriber<String>() {
        override fun onCompleted() {

        }

        override fun onError(e: Throwable?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onNext(sharedPrefsId: String?) {
            Log.e("TEST", "id : $sharedPrefsId")
        }
    }
}