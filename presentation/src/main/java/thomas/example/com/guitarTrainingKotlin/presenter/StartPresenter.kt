package thomas.example.com.guitarTrainingKotlin.presenter

import android.util.Log
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.guitarTrainingKotlin.activity.listener.BaseNavigatorListener
import thomas.example.com.guitarTrainingKotlin.activity.listener.StartNavigatorListener
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.interactor.GetIdInSharedPrefs
import javax.inject.Inject

@PerActivity
class StartPresenter @Inject constructor(baseNavigatorListener: BaseNavigatorListener,
                                         private var getIdInSharedPrefs: GetIdInSharedPrefs,
                                         threadExecutor: ThreadExecutor) {

    private lateinit var startNavigatorListener: StartNavigatorListener

    init {
        if (baseNavigatorListener is StartNavigatorListener) {
            startNavigatorListener = baseNavigatorListener
        }
    }

    /**
     * Using of lambdas !
     */
    fun getUserPrefIsConnected() {
        getIdInSharedPrefs.execute(
                onComplete = {

                },
                onError = {
                    Log.e("TEST", "error : $it")
                },
                onNext = {
                    Log.e("TEST", "id : $it")
                }, params = "test")
    }
}