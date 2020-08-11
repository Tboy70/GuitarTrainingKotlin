package thomas.guitartrainingkotlin.presentation.viewmodel.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.domain.interactor.user.RetrieveProgramsListByUserId
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.program.UserProgramListViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import javax.inject.Inject

class UserProgramsListViewModel @ViewModelInject constructor(
    private val retrieveProgramsListByUserId: RetrieveProgramsListByUserId
) : StateViewModel<UserProgramListViewState>() {

    override val currentViewState =
        UserProgramListViewState()

    private var userId: String? = null

    var retrieveUserProgramLiveData = MutableLiveData<List<ProgramViewDataWrapper>>()

    fun retrieveProgramsListByUserId() {
        viewState.update {
            loading = true
        }
        userId?.let { userId ->
            compositeDisposable?.add(
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
            )
        }
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }
}