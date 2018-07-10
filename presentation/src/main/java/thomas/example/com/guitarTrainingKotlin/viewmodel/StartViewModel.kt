package thomas.example.com.guitarTrainingKotlin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.guitarTrainingKotlin.activity.listener.BaseNavigatorListener
import thomas.example.com.guitarTrainingKotlin.activity.listener.StartNavigatorListener
import thomas.example.com.guitarTrainingKotlin.di.PerActivity
import thomas.example.com.interactor.GetIdInSharedPrefs
import javax.inject.Inject

class StartViewModel @Inject constructor(private var getIdInSharedPrefs: GetIdInSharedPrefs) : ViewModel() {

    private var idUserPref : MutableLiveData<String> = MutableLiveData()

    /**
     * Using of lambdas !
     */
    fun getUserPrefIsConnected(): MutableLiveData<String> {
        getIdInSharedPrefs.execute(
                onComplete = {

                },
                onError = {
                    Log.e("TEST", "error : $it")
                    idUserPref.postValue("test")
                },
                onNext = {
                    Log.e("TEST", "id : $it")
                    idUserPref.postValue(it)
                }, params = "test")

        return idUserPref
    }
}