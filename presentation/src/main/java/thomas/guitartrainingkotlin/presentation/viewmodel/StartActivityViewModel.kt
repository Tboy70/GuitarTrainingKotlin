package thomas.guitartrainingkotlin.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveUserIdInSharedPrefs
import thomas.guitartrainingkotlin.presentation.viewmodel.base.BaseViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent

class StartActivityViewModel @ViewModelInject constructor(
    private val retrieveUserIdInSharedPrefs: RetrieveUserIdInSharedPrefs
) : BaseViewModel() {

    val retrievedUserIdLiveEvent = SingleLiveEvent<String>()

    init {
        getUserIdInSharedPrefs()
    }

    /** Using of lambdas ! **/
    private fun getUserIdInSharedPrefs() {

        viewModelScope.launch {
            try {
                retrieveUserIdInSharedPrefs.retrieveUserIdInSharedPrefs()
                    .collect {
                        retrievedUserIdLiveEvent.postValue(it)
                    }
            } catch (e: Exception) {
                retrievedUserIdLiveEvent.postValue(USER_ID_DEFAULT)
            }
        }
    }

    companion object {
        const val USER_ID_DEFAULT = "USER_ID_DEFAULT"
    }
}