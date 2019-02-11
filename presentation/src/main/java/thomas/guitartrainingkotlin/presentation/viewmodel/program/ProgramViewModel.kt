package thomas.guitartrainingkotlin.presentation.viewmodel.program

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import thomas.guitartrainingkotlin.domain.interactor.program.RetrieveProgramById
import javax.inject.Inject

class ProgramViewModel @Inject constructor(
    private val retrieveProgramById: RetrieveProgramById
) : ViewModel() {

    var errorThrowable: Throwable? = null

    val programRetrieved = MutableLiveData<ProgramViewDataWrapper>()
    val errorEvent =
        SingleLiveEvent<ProgramErrorEvent>()

    data class ProgramErrorEvent(
            val ERROR_TRIGGERED: Boolean = false
    )

    override fun onCleared() {
        super.onCleared()
        retrieveProgramById.unsubscribe()
    }

    fun getProgramById(idProgram: String) {
        retrieveProgramById.subscribe(
                params = RetrieveProgramById.Params.toRetrieve(idProgram),
                onError = {
                    errorThrowable = it
                    errorEvent.postValue(
                        ProgramErrorEvent(
                            ERROR_TRIGGERED = true
                        )
                    )
                },
                onSuccess = {
                    programRetrieved.postValue(
                        ProgramViewDataWrapper(
                            it
                        )
                    )
                }
        )
    }
}