package thomas.guitartrainingkotlin.presentation.viewmodel.program

import android.util.SparseArray
import androidx.core.util.forEach
import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.domain.interactor.program.CreateProgram
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveInstrumentModeInSharedPrefs
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues
import thomas.guitartrainingkotlin.presentation.view.state.program.UserProgramCreationViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import java.util.*
import javax.inject.Inject

class UserProgramCreationViewModel @Inject constructor(
    private val createProgram: CreateProgram,
    private val retrieveInstrumentModeInSharedPrefs: RetrieveInstrumentModeInSharedPrefs
) : StateViewModel<UserProgramCreationViewState>() {

    override val currentViewState = UserProgramCreationViewState()

    val retrievedInstrumentMode = MutableLiveData<Int>()
    val createdProgramLiveEvent = SingleLiveEvent<Boolean>()
    val informationNotRightLiveEvent = SingleLiveEvent<Boolean>()

    private var currentInstrumentMode: Int? = null

    init {
        retrieveCurrentInstrumentMode()
    }

    fun checkInformationAndValidateCreation(
        nameProgram: String,
        descriptionProgram: String,
        exercises: SparseArray<String>
    ) {
        viewState.update {
            loading = true
        }

        if (checkInformation(nameProgram, exercises)) {
            val program = Program(
                nameProgram = nameProgram,
                descriptionProgram = descriptionProgram,
                defaultProgram = false,
                idInstrument = currentInstrumentMode ?: InstrumentModeValues.INSTRUMENT_MODE_GUITAR
            )

            val exercisesList = ArrayList<Exercise>()
            exercises.forEach { key, value ->
                exercisesList.add(
                    Exercise(
                        typeExercise = key,
                        durationExercise = value.toInt()
                    )
                )
            }

            compositeDisposable?.add(
                createProgram.subscribe(
                    params = CreateProgram.Params.toCreate(program, exercisesList),
                    onComplete = {
                        createdProgramLiveEvent.postValue(true)
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
        } else {
            informationNotRightLiveEvent.postValue(true)
            viewState.update {
                loading = false
            }
        }
    }

    private fun checkInformation(nameProgram: String?, exercises: SparseArray<String>): Boolean {
        if (nameProgram == null || nameProgram.isEmpty()) {
            return false
        } else {
            exercises.forEach { key, value ->
                if (key == -1) {
                    return false
                } else if (value.isEmpty() || !value.trim {
                        it <= ' '
                    }.matches("^[0-9]*$".toRegex())) {
                    return false
                }
            }
        }
        return true
    }

    private fun retrieveCurrentInstrumentMode() {
        compositeDisposable?.add(
            retrieveInstrumentModeInSharedPrefs.subscribe(
                onSuccess = { instrumentMode ->
                    currentInstrumentMode = instrumentMode
                    retrievedInstrumentMode.postValue(instrumentMode)
                }, onError = {
                    errorLiveEvent.postValue(it)
                }
            )
        )
    }
}