package thomas.guitartrainingkotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveUserIdInSharedPrefs
import javax.inject.Inject

class StartActivityViewModel @Inject constructor(private val retrieveUserIdInSharedPrefs: RetrieveUserIdInSharedPrefs) : ViewModel() {

    val retrievedUserIdLiveEvent = SingleLiveEvent<String>()

    init {
        getUserIdInSharedPrefs()
    }

    /** To unsubscribe usecase **/
    override fun onCleared() {
        super.onCleared()
        retrieveUserIdInSharedPrefs.unsubscribe()
    }

    /** Using of lambdas ! **/
    private fun getUserIdInSharedPrefs() {
        retrieveUserIdInSharedPrefs.subscribe(
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