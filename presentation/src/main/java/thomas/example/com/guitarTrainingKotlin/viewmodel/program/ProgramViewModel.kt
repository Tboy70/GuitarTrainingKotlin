package thomas.example.com.guitarTrainingKotlin.viewmodel.program

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.ProgramObjectWrapper
import thomas.example.com.interactor.program.RetrieveProgramById
import javax.inject.Inject

class ProgramViewModel @Inject constructor(private val retrieveProgramById: RetrieveProgramById) : ViewModel() {

    lateinit var userProgramObjectWrapper: ProgramObjectWrapper

    val finishRetrieveProgram: MutableLiveData<Boolean> = MutableLiveData()

    fun getProgramById(idProgram: String) {
        retrieveProgramById.execute(
            onComplete = {
            },
            onError = {
                finishRetrieveProgram.postValue(false)
            },
            onNext = {
                userProgramObjectWrapper = ProgramObjectWrapper(it)
                finishRetrieveProgram.postValue(true)

            }, params = RetrieveProgramById.Params.toRetrieve(idProgram)
        )
    }

}