package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.ProgramObjectWrapper
import thomas.example.com.interactor.program.RemoveProgram
import thomas.example.com.interactor.program.RetrieveProgramById
import javax.inject.Inject

class UserProgramDetailsViewModel @Inject constructor(private val retrieveProgramById: RetrieveProgramById,
                                                      private val removeProgram: RemoveProgram) : ViewModel() {

    lateinit var userProgramObjectWrapper: ProgramObjectWrapper

    val finishLoading: MutableLiveData<Boolean> = MutableLiveData()
    val finishRetrieveProgram: MutableLiveData<Boolean> = MutableLiveData()
    val finishProgramDeletion: MutableLiveData<Boolean> = MutableLiveData()

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

    fun removeProgram(idProgram: String) {
        removeProgram.execute(
                onComplete = {
                    finishLoading.postValue(true)
                },
                onError = {
                    finishProgramDeletion.postValue(false)
                },
                onNext = {
                    if (it) {
                        finishProgramDeletion.postValue(true)
                    }

                }, params = RemoveProgram.Params.toRemove(idProgram))
    }

}
