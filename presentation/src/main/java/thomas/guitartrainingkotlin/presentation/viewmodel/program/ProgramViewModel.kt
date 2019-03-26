package thomas.guitartrainingkotlin.presentation.viewmodel.program

import thomas.guitartrainingkotlin.domain.interactor.program.RetrieveProgramById
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.program.ProgramActivityViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import javax.inject.Inject

class ProgramViewModel @Inject constructor(
    private val retrieveProgramById: RetrieveProgramById
) : StateViewModel<ProgramActivityViewState>() {

    override val currentViewState = ProgramActivityViewState()

    val programRetrievedLiveEvent = SingleLiveEvent<ProgramViewDataWrapper>()

    override fun onCleared() {
        super.onCleared()
        retrieveProgramById.unsubscribe()
    }

    fun getProgramById(idProgram: String) {
        retrieveProgramById.subscribe(
            params = RetrieveProgramById.Params.toRetrieve(idProgram),
            onError = {
                errorLiveEvent.postValue(it)
            },
            onSuccess = {
                programRetrievedLiveEvent.postValue(
                    ProgramViewDataWrapper(it)
                )
            }
        )
    }
}