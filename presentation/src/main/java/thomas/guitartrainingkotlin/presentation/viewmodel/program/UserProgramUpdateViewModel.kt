package thomas.guitartrainingkotlin.presentation.viewmodel.program

import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.domain.interactor.program.RetrieveProgramById
import thomas.guitartrainingkotlin.domain.interactor.program.UpdateProgram
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveInstrumentModeInSharedPrefs
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.program.UserProgramUpdateViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import javax.inject.Inject

class UserProgramUpdateViewModel @Inject constructor(
    private val updateProgram: UpdateProgram,
    private val retrieveProgramById: RetrieveProgramById,
    private val retrieveInstrumentModeInSharedPrefs: RetrieveInstrumentModeInSharedPrefs
) : StateViewModel<UserProgramUpdateViewState>() {

    override val currentViewState = UserProgramUpdateViewState()

    private var idProgram: String? = null

    val updateProgramSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val programRetrievedLiveData: MutableLiveData<ProgramViewDataWrapper> = MutableLiveData()
    val instrumentModeRetrievedLiveEvent: SingleLiveEvent<Int> = SingleLiveEvent()

    override fun onCleared() {
        super.onCleared()
        updateProgram.unsubscribe()
        retrieveProgramById.unsubscribe()
        retrieveInstrumentModeInSharedPrefs.unsubscribe()
    }

    fun setIdProgram(idProgram: String) {
        this.idProgram = idProgram
    }

    fun getProgramById() {
        viewState.update {
            loading = true
        }
        this.idProgram?.let { idProgram ->
            retrieveProgramById.subscribe(
                params = RetrieveProgramById.Params.toRetrieve(idProgram),
                onSuccess = {
                    programRetrievedLiveData.postValue(
                        ProgramViewDataWrapper(
                            it
                        )
                    )
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
        }
    }

    fun retrieveInstrumentMode() {
        retrieveInstrumentModeInSharedPrefs.subscribe(
            onSuccess = {
                instrumentModeRetrievedLiveEvent.postValue(it)
            }, onError = {
                errorLiveEvent.postValue(it)
            }
        )
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

            updateProgram.subscribe(
                params = UpdateProgram.Params.toUpdate(program, exercisesToBeRemoved),

                onComplete = {
                    updateProgramSuccess.postValue(true)
                },
                onError = {
                    updateProgramSuccess.postValue(false)
                }
            )
        }
    }

    private fun checkInformation(nameProgram: String, exercises: MutableList<Exercise>): Boolean {
        if (nameProgram.isEmpty()) {
            return false
        } else {
            for (exercise in exercises) {
                if (exercise.typeExercise == -1) {
                    return false
                } else if (!(exercise.durationExercise).toString().trim().matches("^[0-9]*$".toRegex())) {
                    return false
                }
            }
            return true
        }
    }
}