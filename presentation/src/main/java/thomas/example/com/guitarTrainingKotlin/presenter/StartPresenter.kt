package thomas.example.com.guitarTrainingKotlin.presenter

import thomas.example.com.guitarTrainingKotlin.activity.listener.BaseNavigatorListener
import thomas.example.com.guitarTrainingKotlin.activity.listener.StartNavigatorListener
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.interactor.GetIdInSharedPrefs
import javax.inject.Inject

@PerActivity
class StartPresenter @Inject constructor(baseNavigatorListener: BaseNavigatorListener, getIdInSharedPrefs : GetIdInSharedPrefs) {

    private lateinit var startNavigatorListener : StartNavigatorListener

    fun getUserPrefIsConnected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    init {
        if (baseNavigatorListener is StartNavigatorListener) {
         startNavigatorListener = baseNavigatorListener
        }
    }
}