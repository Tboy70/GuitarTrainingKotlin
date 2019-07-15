package thomas.guitartrainingkotlin.presentation.viewmodel

import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveUserIdInSharedPrefs
import thomas.guitartrainingkotlin.presentation.viewmodel.base.BaseViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import javax.inject.Inject

class StartActivityViewModel @Inject constructor(
    private val retrieveUserIdInSharedPrefs: RetrieveUserIdInSharedPrefs
) : BaseViewModel() {

    val retrievedUserIdLiveEvent = SingleLiveEvent<String>()

    init {
        getUserIdInSharedPrefs()
    }

    /** Using of lambdas ! **/
    private fun getUserIdInSharedPrefs() {
        compositeDisposable?.add(
            retrieveUserIdInSharedPrefs.subscribe(
                onSuccess = { userId ->
                    retrievedUserIdLiveEvent.postValue(userId)
                },
                onError = {
                    retrievedUserIdLiveEvent.postValue(USER_ID_DEFAULT)
                }
            )
        )
    }

    companion object {
        const val USER_ID_DEFAULT = "USER_ID_DEFAULT"
    }
}