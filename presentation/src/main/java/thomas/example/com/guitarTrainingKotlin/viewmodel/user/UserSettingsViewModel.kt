package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import thomas.example.com.interactor.sharedprefs.SetInstrumentsModeInSharedPrefs
import javax.inject.Inject

class UserSettingsViewModel @Inject constructor(private val setInstrumentsModeInSharedPrefs: SetInstrumentsModeInSharedPrefs) : ViewModel() {

    val finishSetInstrumentsModeInSharedPrefs: MutableLiveData<Boolean> = MutableLiveData()

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
                }, params = Unit)
    }
}