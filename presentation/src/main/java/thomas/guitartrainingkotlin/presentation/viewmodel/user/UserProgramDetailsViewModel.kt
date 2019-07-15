package thomas.guitartrainingkotlin.presentation.viewmodel.user

import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.domain.interactor.program.RemoveProgram
import thomas.guitartrainingkotlin.domain.interactor.program.RetrieveProgramById
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.program.UserProgramDetailsViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import javax.inject.Inject

class UserProgramDetailsViewModel @Inject constructor(
    private val removeProgram: RemoveProgram,
    private val retrieveProgramById: RetrieveProgramById
) : StateViewModel<UserProgramDetailsViewState>() {

    override val currentViewState = UserProgramDetailsViewState()

    private var idProgram: String? = null
    private var nameProgram: String? = null

    val programRetrievedLiveData = MutableLiveData<ProgramViewDataWrapper>()
    val finishProgramDeletion: MutableLiveData<Boolean> = MutableLiveData()

    fun getIdProgram() = idProgram

    fun setIdProgram(idProgram: String) {
        this.idProgram = idProgram
    }

    fun getProgramById() {
        viewState.update {
            loading = true
        }
        this.idProgram?.let { idProgram ->
            compositeDisposable?.add(
                retrieveProgramById.subscribe(
                    params = RetrieveProgramById.Params.toRetrieve(idProgram),
                    onSuccess = {
                        this.nameProgram = it.nameProgram
                        programRetrievedLiveData.postValue(ProgramViewDataWrapper(it))
                        viewState.update {
                            loading = false
                        }
                    },
                    onError = {
                        errorLiveEvent.postValue(it)
                        viewState.update {
                            loading = false
                        }
                    }
                )
            )
        }
    }

    fun removeProgram() {
        viewState.update {
            loading = true
        }
        idProgram?.let { idProgram ->
            compositeDisposable?.add(
                removeProgram.subscribe(
                    params = RemoveProgram.Params.toRemove(idProgram),
                    onComplete = {
                        finishProgramDeletion.postValue(true)
                        viewState.postValue(
                            UserProgramDetailsViewState(loading = false)
                        )
                    },
                    onError = {
                        errorLiveEvent.postValue(it)
                        viewState.update {
                            loading = false
                        }
                    }
                )
            )
        }
    }
}
