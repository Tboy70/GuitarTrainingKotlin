package thomas.example.com.guitarTrainingKotlin.viewmodel.program

import android.util.SparseArray
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.viewmodel.SingleLiveEvent
import thomas.example.com.interactor.program.CreateProgram
import thomas.example.com.model.Exercise
import thomas.example.com.model.Program
import java.util.*
import javax.inject.Inject

class UserProgramCreationViewModel @Inject constructor(private var createProgram: CreateProgram) : ViewModel() {

    var errorThrowable: Throwable? = null

    val creationProgramSuccess = MutableLiveData<Boolean>()
    val viewState = MutableLiveData<UserProgramCreationViewState>()
    val errorEvent = SingleLiveEvent<UserProgramCreationErrorEvent>()

    data class UserProgramCreationViewState(
            var displayingLoading: Boolean = false
    )

    data class UserProgramCreationErrorEvent(
            val ERROR_TRIGGERED: Boolean = false,
            val ERROR_CREATION_NOT_LAUNCH: Boolean = false
    )


    fun checkInformationAndValidateCreation(
            nameProgram: String, descriptionProgram: String, exercises: SparseArray<String>, instrumentMode: String
    ) {

        viewState.postValue(UserProgramCreationViewState(true))
        if (checkInformation(nameProgram, exercises)) {

            val program = Program()
            program.nameProgram = nameProgram
            program.descriptionProgram = descriptionProgram
            program.defaultProgram = false
            program.idInstrument = instrumentMode

            val exercisesList = ArrayList<Exercise>()

            for (i in 0 until exercises.size()) {
                val key = exercises.keyAt(i)
                val exercise = Exercise()
                exercise.typeExercise = key
                exercise.durationExercise = exercises.get(key).toInt()
                exercisesList.add(exercise)
            }

            createProgram.subscribe(
                    params = CreateProgram.Params.toCreate(program, exercisesList),
                    onComplete = {
                        creationProgramSuccess.postValue(true)
                        viewState.postValue(UserProgramCreationViewState(false))
                    },
                    onError = {
                        errorThrowable = it
                        errorEvent.postValue(UserProgramCreationErrorEvent(true, false))
                    }
            )
        } else {
            errorEvent.postValue(UserProgramCreationErrorEvent(false, true))
            viewState.postValue(UserProgramCreationViewState(false))
        }
    }

    private fun checkInformation(nameProgram: String?, exercises: SparseArray<String>): Boolean {
        if (nameProgram == null || nameProgram.isEmpty()) {
            return false
        } else {
            for (i in 0 until exercises.size()) {
                val key = exercises.keyAt(i)
                if (key == -1) {
                    return false
                } else if (exercises.get(key).isEmpty() || !exercises.get(key).trim { it <= ' ' }.matches("^[0-9]*$".toRegex())) {
                    return false
                }
            }
            return true
        }
    }

    override fun onCleared() {
        super.onCleared()
        createProgram.unsubscribe()
    }
}