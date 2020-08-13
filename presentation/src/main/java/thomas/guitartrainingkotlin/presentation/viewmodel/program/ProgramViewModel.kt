package thomas.guitartrainingkotlin.presentation.viewmodel.program

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.program.RetrieveProgramById
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.program.ProgramActivityViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent

class ProgramViewModel @ViewModelInject constructor(
    private val retrieveProgramById: RetrieveProgramById
) : StateViewModel<ProgramActivityViewState>() {

    override val currentViewState = ProgramActivityViewState()

    val programRetrievedLiveEvent = SingleLiveEvent<ProgramViewDataWrapper>()

    fun getProgramById(idProgram: String) {
        viewModelScope.launch {
            try {
                retrieveProgramById.retrieveProgramById(idProgram)
                    .collect {
                        programRetrievedLiveEvent.postValue(
                            ProgramViewDataWrapper(it)
                        )
                    }
            } catch (e: Exception) {
                errorLiveEvent.postValue(e)
            }
        }
    }
}