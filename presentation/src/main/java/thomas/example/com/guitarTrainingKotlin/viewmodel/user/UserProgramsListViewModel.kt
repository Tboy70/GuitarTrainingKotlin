package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import androidx.lifecycle.MutableLiveData
import thomas.example.com.guitarTrainingKotlin.view.datawrapper.ProgramViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.view.state.program.UserProgramListViewState
import thomas.example.com.guitarTrainingKotlin.viewmodel.base.StateViewModel
import thomas.example.com.interactor.user.RetrieveProgramsListByUserId
import javax.inject.Inject

class UserProgramsListViewModel @Inject constructor(
    private val retrieveProgramsListByUserId: RetrieveProgramsListByUserId
) : StateViewModel<UserProgramListViewState>() {

    override val currentViewState =
        UserProgramListViewState()

    private var userId: String? = null

    var retrieveUserProgramLiveData = MutableLiveData<List<ProgramViewDataWrapper>>()

    override fun onCleared() {
        super.onCleared()
        retrieveProgramsListByUserId.unsubscribe()
    }

    fun retrieveProgramsListByUserId() {
        viewState.update {
            loading = true
        }
        userId?.let { userId ->
            retrieveProgramsListByUserId.subscribe(
                params = RetrieveProgramsListByUserId.Params.forList(userId),
                onSuccess = {
                    retrieveUserProgramLiveData.postValue(it.map { program ->
                        ProgramViewDataWrapper(program)
                    })
                    viewState.update {
                        loading = false
                    }
                },
                onError = { throwable ->
                    errorLiveEvent.postValue(throwable)
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
}