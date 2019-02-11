package thomas.guitartrainingkotlin.presentation.viewmodel.user

import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.presentation.view.state.user.UserSettingsViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveInstrumentModeInSharedPrefs
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.SetInstrumentModeInSharedPrefs
import thomas.guitartrainingkotlin.domain.interactor.user.SuppressAccount
import javax.inject.Inject

class UserSettingsViewModel @Inject constructor(
    private val suppressAccount: SuppressAccount,
    private val setInstrumentModeInSharedPrefs: SetInstrumentModeInSharedPrefs,
    private val retrieveInstrumentModeInSharedPrefs: RetrieveInstrumentModeInSharedPrefs
) : StateViewModel<UserSettingsViewState>() {

    override val currentViewState = UserSettingsViewState()

    private var userId: String? = null
    private var currentInstrumentMode: Int? = null

    val suppressedAccountLiveEvent =
        SingleLiveEvent<Boolean>()
    val retrievedInstrumentModeLiveData = MutableLiveData<Int>()

    init {
        retrieveCurrentInstrumentMode()
    }

    override fun onCleared() {
        super.onCleared()
        suppressAccount.unsubscribe()
        setInstrumentModeInSharedPrefs.unsubscribe()
        retrieveInstrumentModeInSharedPrefs.unsubscribe()
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
        retrieveInstrumentModeInSharedPrefs.subscribe(
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