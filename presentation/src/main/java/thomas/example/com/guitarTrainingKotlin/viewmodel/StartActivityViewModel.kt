package thomas.example.com.guitarTrainingKotlin.viewmodel

import androidx.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.sharedprefs.GetUserIdInSharedPrefs
import javax.inject.Inject

class StartActivityViewModel @Inject constructor(private val getUserIdInSharedPrefs: GetUserIdInSharedPrefs) : ViewModel() {

    val retrievedUserIdLiveEvent = SingleLiveEvent<String>()

    init {
        getUserIdInSharedPrefs()
    }

    /** To unsubscribe usecase **/
    override fun onCleared() {
        super.onCleared()
        getUserIdInSharedPrefs.unsubscribe()
    }

    /** Using of lambdas ! **/
    private fun getUserIdInSharedPrefs() {
        getUserIdInSharedPrefs.subscribe(
            onSuccess = { userId ->
                retrievedUserIdLiveEvent.postValue(userId)
            },
            onError = {
                retrievedUserIdLiveEvent.postValue(USER_ID_DEFAULT)
            }
        )
    }

    companion object {
        const val USER_ID_DEFAULT = "USER_ID_DEFAULT"
    }
}