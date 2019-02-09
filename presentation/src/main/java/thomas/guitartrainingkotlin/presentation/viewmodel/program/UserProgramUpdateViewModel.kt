package thomas.guitartrainingkotlin.presentation.viewmodel.program

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.guitartrainingkotlin.domain.interactor.program.UpdateProgram
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program
import javax.inject.Inject

class UserProgramUpdateViewModel @Inject constructor(private var updateProgram: UpdateProgram) : ViewModel() {

    val updateProgramSuccess: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null

    fun checkInformationAndValidateUpdate(
            idProgram: String, nameProgram: String, descriptionProgram: String,
            programListExercises: MutableList<Exercise>, exercisesToBeRemoved: MutableList<Exercise>
    ) {
        if (checkInformation(nameProgram, programListExercises)) {
            val program = Program()
            program.idProgram = idProgram
            program.nameProgram = nameProgram
            program.descriptionProgram = descriptionProgram
            program.defaultProgram = false
            program.exercises = programListExercises

            updateProgram.subscribe(
                    params = UpdateProgram.Params.toUpdate(program, exercisesToBeRemoved),

                    onComplete = {
                        updateProgramSuccess.postValue(true)
                    },
                    onError = {
                        errorThrowable = it
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

    override fun onCleared() {
        super.onCleared()
        updateProgram.unsubscribe()
    }
}