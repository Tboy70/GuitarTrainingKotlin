package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.ProgramViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.viewmodel.livedata.SingleLiveEvent
import thomas.example.com.interactor.program.RemoveProgram
import thomas.example.com.interactor.program.RetrieveProgramById
import javax.inject.Inject

class UserProgramDetailsViewModel @Inject constructor(
        private val retrieveProgramById: RetrieveProgramById,
        private val removeProgram: RemoveProgram
) : ViewModel() {

    lateinit var userProgramViewDataWrapper: ProgramViewDataWrapper

    val programDetailsRetrieved = MutableLiveData<ProgramViewDataWrapper>()
    val finishProgramDeletion: MutableLiveData<Boolean> = MutableLiveData()
    val viewState = MutableLiveData<UserProgramDetailsViewState>()
    val errorEvent =
        SingleLiveEvent<UserProgramDetailsErrorEvent>()

    var errorThrowable: Throwable? = null

    data class UserProgramDetailsViewState(
            var displayingLoadingGetProgram: Boolean = false,
            var displayLoadingRemoveProgram: Boolean = false
    )

    data class UserProgramDetailsErrorEvent(
            val ERROR_TRIGGERED: Boolean = false
    )

    fun getProgramById(idProgram: String) {

        viewState.postValue(UserProgramDetailsViewState(true, false))

        retrieveProgramById.subscribe(
                params = RetrieveProgramById.Params.toRetrieve(idProgram),
                onSuccess = {
                    programDetailsRetrieved.postValue(ProgramViewDataWrapper(it))
                    viewState.postValue(UserProgramDetailsViewState(false, false))
                },
                onError = {
                    errorThrowable = it
                    errorEvent.postValue(UserProgramDetailsErrorEvent(true))
                    viewState.postValue(UserProgramDetailsViewState(false, false))
                }
        )
    }

    fun removeProgram(idProgram: String) {

        viewState.postValue(UserProgramDetailsViewState(false, true ))

        removeProgram.subscribe(
                params = RemoveProgram.Params.toRemove(idProgram),
                onComplete = {
                    finishProgramDeletion.postValue(true)
                    viewState.postValue(UserProgramDetailsViewState(false, false))
                },
                onError = {
                    errorThrowable = it
                    errorEvent.postValue(UserProgramDetailsErrorEvent(true))
                    viewState.postValue(UserProgramDetailsViewState(false, false))
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        retrieveProgramById.unsubscribe()
        removeProgram.unsubscribe()
    }
}
