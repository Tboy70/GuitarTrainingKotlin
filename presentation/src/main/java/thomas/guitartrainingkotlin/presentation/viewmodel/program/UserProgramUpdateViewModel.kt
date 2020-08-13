package thomas.guitartrainingkotlin.presentation.viewmodel.program

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.program.RetrieveProgramById
import thomas.guitartrainingkotlin.domain.interactor.program.UpdateProgram
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveInstrumentModeInSharedPrefs
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.program.UserProgramUpdateViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent

@ExperimentalCoroutinesApi
class UserProgramUpdateViewModel @ViewModelInject constructor(
    private val updateProgram: UpdateProgram,
    private val retrieveProgramById: RetrieveProgramById,
    private val retrieveInstrumentModeInSharedPrefs: RetrieveInstrumentModeInSharedPrefs
) : StateViewModel<UserProgramUpdateViewState>() {

    override val currentViewState = UserProgramUpdateViewState()

    private var idProgram: String? = null

    val updateProgramSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val programRetrievedLiveData: MutableLiveData<ProgramViewDataWrapper> = MutableLiveData()
    val instrumentModeRetrievedLiveEvent: SingleLiveEvent<Int> = SingleLiveEvent()

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

    fun retrieveInstrumentMode() {
        viewModelScope.launch {
            try {
                retrieveInstrumentModeInSharedPrefs.retrieveInstrumentModeInSharedPrefs()
                    .collect {
                        instrumentModeRetrievedLiveEvent.postValue(it)
                    }
            } catch (e: Exception) {
                errorLiveEvent.postValue(e)
            }
        }
    }

    fun checkInformationAndValidateUpdate(
        idProgram: String, nameProgram: String, descriptionProgram: String,
        programListExercises: MutableList<Exercise>, exercisesToBeRemoved: MutableList<Exercise>
    ) {
        if (checkInformation(nameProgram, programListExercises)) {
            val program = Program(
                idProgram = idProgram,
                nameProgram = nameProgram,
                descriptionProgram = descriptionProgram,
                defaultProgram = false,
                exercises = programListExercises
            )

            viewModelScope.launch {
                try {
                    updateProgram.updateProgram(program, exercisesToBeRemoved)
                        .collect {
                            updateProgramSuccess.postValue(true)
                        }
                } catch (e: Exception) {
                    updateProgramSuccess.postValue(false)
                    errorLiveEvent.postValue(e)
                }
            }
        }
    }

    private fun checkInformation(nameProgram: String, exercises: MutableList<Exercise>): Boolean {
        if (nameProgram.isEmpty()) {
            return false
        } else {
            for (exercise in exercises) {
                if (exercise.typeExercise == -1) {
                    return false
                } else if (!(exercise.durationExercise).toString().trim()
                        .matches("^[0-9]*$".toRegex())
                ) {
                    return false
                }
            }
            return true
        }
    }
}