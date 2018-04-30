package thomas.example.com.guitarTrainingKotlin.presenter

import thomas.example.com.guitarTrainingKotlin.activity.listener.BaseNavigatorListener
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.sharedprefs.GetIdInSharedPrefs
import javax.inject.Inject

@PerActivity
class StartPresenter @Inject constructor(baseNavigatorListener: BaseNavigatorListener, getIdInSharedPrefs: GetIdInSharedPrefs) {

    fun getUserPrefIsConnected() {

    }
}