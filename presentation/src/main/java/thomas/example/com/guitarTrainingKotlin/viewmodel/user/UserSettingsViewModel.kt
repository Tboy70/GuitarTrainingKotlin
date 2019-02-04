package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import androidx.lifecycle.MutableLiveData
import thomas.example.com.guitarTrainingKotlin.view.state.user.UserSettingsViewState
import thomas.example.com.guitarTrainingKotlin.viewmodel.base.StateViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.sharedprefs.RetrieveInstrumentModeInSharedPrefs
import thomas.example.com.interactor.sharedprefs.SetInstrumentModeInSharedPrefs
import thomas.example.com.interactor.user.SuppressAccount
import javax.inject.Inject

class UserSettingsViewModel @Inject constructor(
    private val suppressAccount: SuppressAccount,
    private val retrieveInstrumentsModeInSharedPrefs: RetrieveInstrumentModeInSharedPrefs,
    private val setInstrumentModeInSharedPrefs: SetInstrumentModeInSharedPrefs
) : StateViewModel<UserSettingsViewState>() {

    override val currentViewState = UserSettingsViewState()

    private var userId: String? = null
    private var currentInstrumentMode: String? = null

    val suppressedAccountLiveEvent = SingleLiveEvent<Boolean>()
    val retrievedInstrumentModeLiveData = MutableLiveData<String>()

    init {
        retrieveCurrentInstrumentMode()
    }

    override fun onCleared() {
        super.onCleared()
        setInstrumentModeInSharedPrefs.unsubscribe()
        suppressAccount.unsubscribe()
    }

    fun updateInstrumentMode() {
        currentInstrumentMode?.let {
            setInstrumentModeInSharedPrefs.subscribe(
                onSuccess = { newInstrumentMode ->
                    currentInstrumentMode = newInstrumentMode
                    retrievedInstrumentModeLiveData.postValue(currentInstrumentMode)
                },
                onError = { throwable ->
                    errorLiveEvent.postValue(throwable)
                },
                params = SetInstrumentModeInSharedPrefs.Params.toSet(it)
            )
        }
    }

    fun suppressAccount() {
        userId?.let { userId ->
            viewState.update {
                loading = true
            }
            suppressAccount.subscribe(
                params = SuppressAccount.Params.toSuppress(userId),
                onComplete = {
                    suppressedAccountLiveEvent.postValue(true)
                    viewState.update {
                        loading = false
                    }
                },
                onError = {
                    viewState.update {
                        loading = false
                    }
                }
            )
        }
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }

    private fun retrieveCurrentInstrumentMode() {
        retrieveInstrumentsModeInSharedPrefs.subscribe(
            onSuccess = {
                currentInstrumentMode = it
                retrievedInstrumentModeLiveData.postValue(it)
            },
            onError = {
                errorLiveEvent.postValue(it)
            }
        )
    }
}