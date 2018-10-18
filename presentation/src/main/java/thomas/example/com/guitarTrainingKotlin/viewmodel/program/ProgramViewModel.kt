package thomas.example.com.guitarTrainingKotlin.viewmodel.program

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.ProgramObjectWrapper
import thomas.example.com.guitarTrainingKotlin.viewmodel.SingleLiveEvent
import thomas.example.com.interactor.program.RetrieveProgramById
import javax.inject.Inject

class ProgramViewModel @Inject constructor(private val retrieveProgramById: RetrieveProgramById) : ViewModel() {

    var errorThrowable: Throwable? = null

    val programRetrieved = MutableLiveData<ProgramObjectWrapper>()
    val errorEvent = SingleLiveEvent<ProgramErrorEvent>()

    data class ProgramErrorEvent(
            val ERROR_TRIGGERED: Boolean = false
    )

    fun getProgramById(idProgram: String) {
        retrieveProgramById.subscribe(
                params = RetrieveProgramById.Params.toRetrieve(idProgram),
                onError = {
                    errorThrowable = it
                    errorEvent.postValue(ProgramErrorEvent(ERROR_TRIGGERED = true))
                },
                onSuccess = {
                    programRetrieved.postValue(ProgramObjectWrapper(it))
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        retrieveProgramById.unsubscribe()
    }
}