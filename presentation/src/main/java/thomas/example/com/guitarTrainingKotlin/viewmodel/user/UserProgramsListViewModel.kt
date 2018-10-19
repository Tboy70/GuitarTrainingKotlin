package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.ProgramViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.viewmodel.SingleLiveEvent
import thomas.example.com.interactor.user.RetrieveProgramsListByUserId
import javax.inject.Inject

class UserProgramsListViewModel @Inject constructor(
        private val retrieveProgramsListByUserId: RetrieveProgramsListByUserId
) : ViewModel() {

    var userPrograms = MutableLiveData<List<ProgramViewDataWrapper>>()
    val viewState = MutableLiveData<UserProgramsListViewState>()
    val errorEvent = SingleLiveEvent<UserProgramsListErrorEvent>()

    var errorThrowable: Throwable? = null

    data class UserProgramsListViewState(
            var refreshList: Boolean = false
    )

    data class UserProgramsListErrorEvent(
            val ERROR_TRIGGERED: Boolean = false
    )

    init {
        // TODO : Launch method here ? Same for other view models ?
    }

    fun retrieveProgramsListByUserId(idUser: String) {

        viewState.postValue(UserProgramsListViewState(true))

        retrieveProgramsListByUserId.subscribe(
                params = RetrieveProgramsListByUserId.Params.forList(idUser),
                onSuccess = {
                    userPrograms.postValue(it.map { program ->
                        ProgramViewDataWrapper(program)
                    })
                    viewState.postValue(UserProgramsListViewState(false))
                },
                onError = {
                    errorThrowable = it
                    errorEvent.postValue(UserProgramsListErrorEvent(true))
                    viewState.postValue(UserProgramsListViewState(false))
                }
        )
    }

    fun getIdUser(context: Context): String {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(ModuleSharedPrefsImpl.CURRENT_USER_ID, "0")
    }

    override fun onCleared() {
        super.onCleared()
        retrieveProgramsListByUserId.unsubscribe()
    }
}