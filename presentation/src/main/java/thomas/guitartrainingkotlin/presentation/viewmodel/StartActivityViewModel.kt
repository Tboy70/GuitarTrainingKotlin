package thomas.guitartrainingkotlin.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveUserId
import thomas.guitartrainingkotlin.presentation.viewmodel.base.BaseViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent

class StartActivityViewModel @ViewModelInject constructor(
    private val retrieveUserId: RetrieveUserId
) : BaseViewModel() {

    val retrievedUserIdLiveEvent = SingleLiveEvent<String?>()

    init {
        getUserId()
    }

    private fun getUserId() {

        viewModelScope.launch {
            try {
                retrieveUserId.retrieveUserId()
                    .collect { userId ->
                        retrievedUserIdLiveEvent.postValue(userId)
                    }
            } catch (e: Exception) {
                retrievedUserIdLiveEvent.postValue(null)
            }
        }
    }
}