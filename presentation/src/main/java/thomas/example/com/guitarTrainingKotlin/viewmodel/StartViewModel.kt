package thomas.example.com.guitarTrainingKotlin.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import thomas.example.com.interactor.GetIdInSharedPrefs
import javax.inject.Inject

class StartViewModel @Inject constructor(private var getIdInSharedPrefs: GetIdInSharedPrefs) : ViewModel() {

    private var idUserPref: MutableLiveData<String> = MutableLiveData()

    /**
     * Using of lambdas !
     */
    fun getUserPrefIsConnected(): MutableLiveData<String> {
        getIdInSharedPrefs.execute(
                onComplete = {

                },
                onError = {
                    idUserPref.postValue(GetIdInSharedPrefs.ID_USER_DEFAULT)
                },
                onNext = {
                    idUserPref.postValue(it)
                }, params = "")

        return idUserPref
    }
}