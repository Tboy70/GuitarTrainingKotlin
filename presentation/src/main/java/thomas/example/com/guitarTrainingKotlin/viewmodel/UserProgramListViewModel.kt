package thomas.example.com.guitarTrainingKotlin.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.interactor.user.RetrieveProgramsListByUserId
import thomas.example.com.model.Program
import javax.inject.Inject

class UserProgramListViewModel @Inject constructor(private val retrieveProgramsListByUserId: RetrieveProgramsListByUserId) : ViewModel() {

    lateinit var userPrograms: List<Program>
    val finishRetrievePrograms: MutableLiveData<Boolean> = MutableLiveData()

    fun retrieveProgramsListByUserId(idUser: String) {
        retrieveProgramsListByUserId.execute(
                onComplete = {
                },
                onError = {
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