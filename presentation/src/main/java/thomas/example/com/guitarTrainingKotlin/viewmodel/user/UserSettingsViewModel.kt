package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.SingleLiveEvent
import thomas.example.com.guitarTrainingKotlin.viewmodel.login.LoginHomeViewModel
import thomas.example.com.interactor.sharedprefs.SetInstrumentsModeInSharedPrefs
import thomas.example.com.interactor.user.SuppressAccount
import javax.inject.Inject

class UserSettingsViewModel @Inject constructor(
        private val setInstrumentsModeInSharedPrefs: SetInstrumentsModeInSharedPrefs,
        private val suppressAccount: SuppressAccount
) : ViewModel() {

    val viewState = MutableLiveData<UserSettingsViewState>()
    val errorEvent = SingleLiveEvent<UserSettingsErrorEvent>()

    val finishSuppressAccount: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null

    data class UserSettingsViewState(
            val displayingLoading: Boolean = false
    )

    data class UserSettingsErrorEvent(
            val ERROR_TRIGGERED: Boolean = false
    )

    fun updateInstrumentMode() {
        setInstrumentsModeInSharedPrefs.subscribe(
                onComplete = {
                },
                onError = {
                    errorThrowable = it
                    errorEvent.postValue(UserSettingsErrorEvent(true))
                }
        )
    }

    fun suppressAccount(idUser: String) {
        viewState.postValue(UserSettingsViewState(true))
        suppressAccount.subscribe(
                params = SuppressAccount.Params.toSuppress(idUser),
                onComplete = {
                    finishSuppressAccount.postValue(true)
                    viewState.postValue(UserSettingsViewState(false))
                },
                onError = {
                    errorThrowable = it
                    errorEvent.postValue(UserSettingsErrorEvent(true))
                    viewState.postValue(UserSettingsViewState(false))
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        setInstrumentsModeInSharedPrefs.unsubscribe()
        suppressAccount.unsubscribe()
    }
}