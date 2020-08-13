package thomas.guitartrainingkotlin.presentation.viewmodel.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveInstrumentModeInSharedPrefs
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.SetInstrumentModeInSharedPrefs
import thomas.guitartrainingkotlin.domain.interactor.user.SuppressAccount
import thomas.guitartrainingkotlin.presentation.view.state.user.UserSettingsViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent

@ExperimentalCoroutinesApi
class UserSettingsViewModel @ViewModelInject constructor(
    private val suppressAccount: SuppressAccount,
    private val setInstrumentModeInSharedPrefs: SetInstrumentModeInSharedPrefs,
    private val retrieveInstrumentModeInSharedPrefs: RetrieveInstrumentModeInSharedPrefs
) : StateViewModel<UserSettingsViewState>() {

    override val currentViewState = UserSettingsViewState()

    private var userId: String? = null
    private var currentInstrumentMode: Int? = null

    val suppressedAccountLiveEvent = SingleLiveEvent<Boolean>()
    val retrievedInstrumentModeLiveData = MutableLiveData<Int>()

    init {
        retrieveCurrentInstrumentMode()
    }

    fun updateInstrumentMode() {
        currentInstrumentMode?.let {

            viewModelScope.launch {
                try {
                    setInstrumentModeInSharedPrefs.setInstrumentModeInSharedPrefs(it)
                        .collect { newInstrumentMode ->
                            currentInstrumentMode = newInstrumentMode
                            retrievedInstrumentModeLiveData.postValue(currentInstrumentMode)
                        }
                } catch (e: Exception) {
                    errorLiveEvent.postValue(e)
                }
            }
        }
    }

    fun suppressAccount() {
        userId?.let { userId ->

            viewModelScope.launch {
                try {
                    suppressAccount.suppressAccount(userId)
                        .onStart { viewState.update { loading = true } }
                        .onCompletion { viewState.update { loading = false } }
                        .collect { suppressedAccountLiveEvent.postValue(true) }
                } catch (e: Exception) {
                    errorLiveEvent.postValue(e)
                }
            }
        }
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }

    private fun retrieveCurrentInstrumentMode() {
        viewModelScope.launch {
            try {
                retrieveInstrumentModeInSharedPrefs.retrieveInstrumentModeInSharedPrefs()
                    .collect {
                        currentInstrumentMode = it
                        retrievedInstrumentModeLiveData.postValue(it)
                    }
            } catch (e: Exception) {
                errorLiveEvent.postValue(e)
            }
        }
    }
}