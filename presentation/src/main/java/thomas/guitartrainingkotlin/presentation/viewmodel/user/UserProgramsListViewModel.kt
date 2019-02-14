package thomas.guitartrainingkotlin.presentation.viewmodel.user

import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.program.UserProgramListViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.domain.interactor.user.RetrieveProgramsListByUserId
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