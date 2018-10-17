package thomas.example.com.guitarTrainingKotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.interactor.sharedprefs.GetIdInSharedPrefs
import javax.inject.Inject

class StartViewModel @Inject constructor(private var getIdInSharedPrefs: GetIdInSharedPrefs) : ViewModel() {

    val idUserPref: MutableLiveData<String> = MutableLiveData()

    /** Using of lambdas ! **/
    fun getUserPrefIsConnected() {
        getIdInSharedPrefs.execute(
            onComplete = {

            },
            onError = {
                idUserPref.postValue(GetIdInSharedPrefs.ID_USER_DEFAULT)
            },
            onNext = {
                idUserPref.postValue(it)
            }, params = ""
        )
    }
}