package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import thomas.example.com.data.manager.SharedPrefsManagerImpl
import thomas.example.com.guitarTrainingKotlin.view.state.UserSettingsViewState
import thomas.example.com.guitarTrainingKotlin.viewmodel.base.AndroidStateViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.sharedprefs.SetInstrumentsModeInSharedPrefs
import thomas.example.com.interactor.user.SuppressAccount
import javax.inject.Inject

class UserSettingsViewModel @Inject constructor(
    application: Application,
    private val suppressAccount: SuppressAccount,
    private val getInstrumentsModeInSharedPrefs: RetrIn,
    private val setInstrumentsModeInSharedPrefs: SetInstrumentsModeInSharedPrefs
) : AndroidStateViewModel<UserSettingsViewState>(application) {

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
        setInstrumentsModeInSharedPrefs.unsubscribe()
        suppressAccount.unsubscribe()
    }

    fun updateInstrumentMode() {
        currentInstrumentMode?.let {
            setInstrumentsModeInSharedPrefs.subscribe(
                onComplete = {
                },
                onError = {

                },
                params = SetInstrumentsModeInSharedPrefs.Params.toSet(it)
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
        PreferenceManager.getDefaultSharedPreferences(getApplication())
            .getString(SharedPrefsManagerImpl.CURRENT_INSTRUMENT_MODE, SharedPrefsManagerImpl.INSTRUMENT_MODE_GUITAR)
            ?.let { currentInstrumentMode ->
                this.currentInstrumentMode = currentInstrumentMode
                retrievedInstrumentModeLiveData.postValue(currentInstrumentMode)
            }
    }
}