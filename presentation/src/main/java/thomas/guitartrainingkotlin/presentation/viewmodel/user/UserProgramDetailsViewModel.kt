package thomas.guitartrainingkotlin.presentation.viewmodel.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.program.RemoveProgram
import thomas.guitartrainingkotlin.domain.interactor.program.RetrieveProgramById
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.program.UserProgramDetailsViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel

@ExperimentalCoroutinesApi
class UserProgramDetailsViewModel @ViewModelInject constructor(
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

        idProgram?.let {
            viewModelScope.launch {
                try {
                    retrieveProgramById.retrieveProgramById(it)
                        .onStart { viewState.update { loading = true } }
                        .onCompletion { viewState.update { loading = false } }
                        .collect {
                            programRetrievedLiveData.postValue(
                                ProgramViewDataWrapper(
                                    it
                                )
                            )
                        }
                } catch (e: Exception) {
                    errorLiveEvent.postValue(e)
                }
            }
        }
    }

    fun removeProgram() {

        idProgram?.let {
            viewModelScope.launch {
                try {
                    removeProgram.removeProgram(it)
                        .onStart { viewState.update { loading = true } }
                        .onCompletion { viewState.update { loading = false } }
                        .collect {
                            finishProgramDeletion.postValue(true)
                        }
                } catch (e : Exception) {
                    errorLiveEvent.postValue(e)
                }
            }
        }
    }
}
