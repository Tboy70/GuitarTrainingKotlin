package thomas.example.com.guitarTrainingKotlin.viewmodel

import androidx.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.sharedprefs.GetUserIdInSharedPrefs
import javax.inject.Inject

class StartActivityViewModel @Inject constructor(private val getUserUserIdInSharedPrefs: GetUserIdInSharedPrefs) : ViewModel() {

    val retrievedUserIdLiveData = SingleLiveEvent<String>()

    init {
        getUserIdInSharedPrefs()
    }

    override fun onCleared() {
        super.onCleared()
        getUserUserIdInSharedPrefs.unsubscribe()
    }

    /** Using of lambdas ! **/
    private fun getUserIdInSharedPrefs() {
        getUserUserIdInSharedPrefs.subscribe(
            onSuccess = { userId ->
                retrievedUserIdLiveData.postValue(userId)
            },
            onError = {
                retrievedUserIdLiveData.postValue(USER_ID_DEFAULT)
            }
        )
    }

    companion object {
        const val USER_ID_DEFAULT = "USER_ID_DEFAULT"
    }
}