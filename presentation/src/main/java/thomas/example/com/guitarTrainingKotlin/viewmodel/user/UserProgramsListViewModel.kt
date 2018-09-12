package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.interactor.user.RetrieveProgramsListByUserId
import thomas.example.com.model.Program
import javax.inject.Inject

class UserProgramsListViewModel @Inject constructor(private val retrieveProgramsListByUserId: RetrieveProgramsListByUserId) : ViewModel() {

    var userPrograms: List<Program> = ArrayList()

    val finishRetrievePrograms: MutableLiveData<Boolean> = MutableLiveData()

    val refreshList: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null

    fun retrieveProgramsListByUserId(idUser: String) {
        refreshList.postValue(true)
        retrieveProgramsListByUserId.execute(
                onComplete = {
                    refreshList.postValue(false)
                },
                onError = {
                    errorThrowable = it
                    refreshList.postValue(false)
                    finishRetrievePrograms.postValue(false)
                },
                onNext = {
                    userPrograms = it
                    finishRetrievePrograms.postValue(true)
                }, params = RetrieveProgramsListByUserId.Params.forList(idUser))
    }

    fun getIdUser(context: Context): String {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(ModuleSharedPrefsImpl.CURRENT_USER_ID, "0")
    }
}