package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.interactor.sharedprefs.SetInstrumentsModeInSharedPrefs
import thomas.example.com.interactor.user.SuppressAccount
import javax.inject.Inject

class UserSettingsViewModel @Inject constructor(
    private val setInstrumentsModeInSharedPrefs: SetInstrumentsModeInSharedPrefs,
    private val suppressAccount: SuppressAccount
) : ViewModel() {

    val finishSetInstrumentsModeInSharedPrefs: MutableLiveData<Boolean> = MutableLiveData()
    val finishSuppressAccount: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null

    fun updateInstrumentMode() {
        setInstrumentsModeInSharedPrefs.execute(
            onComplete = {

            },
            onError = {
                errorThrowable = it
                finishSetInstrumentsModeInSharedPrefs.postValue(false)
            },
            onNext = {
                finishSetInstrumentsModeInSharedPrefs.postValue(true)
            }, params = Unit
        )
    }

    fun suppressAccount(idUser: String) {
        suppressAccount.execute(
            onComplete = {

            },
            onError = {
                errorThrowable = it
                finishSuppressAccount.postValue(false)
            },
            onNext = {
                finishSuppressAccount.postValue(true)
            }, params = SuppressAccount.Params.toSuppress(idUser)
        )
    }
}