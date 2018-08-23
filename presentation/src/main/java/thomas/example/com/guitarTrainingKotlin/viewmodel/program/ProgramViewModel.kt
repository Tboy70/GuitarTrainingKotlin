package thomas.example.com.guitarTrainingKotlin.viewmodel.program

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.ProgramObjectWrapper
import thomas.example.com.interactor.program.RetrieveProgramById
import javax.inject.Inject

class ProgramViewModel @Inject constructor(private val retrieveProgramById: RetrieveProgramById) : ViewModel() {

    lateinit var userProgramObjectWrapper: ProgramObjectWrapper

    val finishLoading: MutableLiveData<Boolean> = MutableLiveData()
    val finishRetrieveProgram: MutableLiveData<Boolean> = MutableLiveData()

    fun getProgramById(idProgram: String) {
        retrieveProgramById.execute(
                onComplete = {
                    finishLoading.postValue(true)
                },
                onError = {
                    finishRetrieveProgram.postValue(false)
                },
                onNext = {
                    userProgramObjectWrapper = ProgramObjectWrapper(it)
                    finishRetrieveProgram.postValue(true)

                }, params = RetrieveProgramById.Params.toRetrieve(idProgram))
    }

}