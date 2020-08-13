package thomas.guitartrainingkotlin.presentation.viewmodel.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.user.RetrieveProgramsListByUserId
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.program.UserProgramListViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel

@ExperimentalCoroutinesApi
class UserProgramsListViewModel @ViewModelInject constructor(
    private val retrieveProgramsListByUserId: RetrieveProgramsListByUserId
) : StateViewModel<UserProgramListViewState>() {

    override val currentViewState =
        UserProgramListViewState()

    private var userId: String? = null

    var retrieveUserProgramLiveData = MutableLiveData<List<ProgramViewDataWrapper>>()

    fun retrieveProgramsListByUserId() {

        userId?.let {
            viewModelScope.launch {
                try {
                    retrieveProgramsListByUserId.retrieveProgramsListByUserId(it)
                        .onStart { viewState.update { loading = true } }
                        .onCompletion { viewState.update { loading = false } }
                        .collect {
                            retrieveUserProgramLiveData.postValue(it.map { program ->
                                ProgramViewDataWrapper(program)
                            })
                        }
                } catch (e: Exception) {
                    errorLiveEvent.postValue(e)
                }
            }
        }

//        viewState.update {
//            loading = true
//        }
//        userId?.let { userId ->
//            compositeDisposable?.add(
//                retrieveProgramsListByUserId.subscribe(
//                    params = RetrieveProgramsListByUserId.Params.forList(userId),
//                    onSuccess = {
//                        retrieveUserProgramLiveData.postValue(it.map { program ->
//                            ProgramViewDataWrapper(program)
//                        })
//                        viewState.update {
//                            loading = false
//                        }
//                    },
//                    onError = { throwable ->
//                        errorLiveEvent.postValue(throwable)
//                        viewState.update {
//                            loading = false
//                        }
//                    }
//                )
//            )
//        }
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }
}