package thomas.guitartrainingkotlin.presentation.viewmodel.program

import androidx.hilt.lifecycle.ViewModelInject
import thomas.guitartrainingkotlin.domain.interactor.program.RetrieveProgramById
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.program.ProgramActivityViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import javax.inject.Inject

class ProgramViewModel @ViewModelInject constructor(
    private val retrieveProgramById: RetrieveProgramById
) : StateViewModel<ProgramActivityViewState>() {

    override val currentViewState = ProgramActivityViewState()

    val programRetrievedLiveEvent = SingleLiveEvent<ProgramViewDataWrapper>()

    fun getProgramById(idProgram: String) {
        compositeDisposable?.add(
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
        )
    }
}