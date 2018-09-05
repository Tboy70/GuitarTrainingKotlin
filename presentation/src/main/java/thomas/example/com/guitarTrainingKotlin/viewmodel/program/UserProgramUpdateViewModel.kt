package thomas.example.com.guitarTrainingKotlin.viewmodel.program

import android.arch.lifecycle.ViewModel
import android.util.Log
import thomas.example.com.interactor.program.UpdateProgram
import thomas.example.com.model.Exercise
import thomas.example.com.model.Program
import javax.inject.Inject

class UserProgramUpdateViewModel @Inject constructor(private var updateProgram: UpdateProgram) : ViewModel() {

    fun checkInformationAndValidateUpdate(idProgram: String, nameProgram: String, descriptionProgram: String, programListExercises: MutableList<Exercise>, exercisesToBeRemoved: MutableList<Exercise>) {
        if (checkInformation(nameProgram, programListExercises)) {
            val program = Program()
            program.idProgram = idProgram
            program.nameProgram = nameProgram
            program.descriptionProgram = descriptionProgram
            program.defaultProgram = false
            program.exercises = programListExercises

            updateProgram.execute(
                    onComplete = {

                    },
                    onError = {


                    },
                    onNext = {
                        Log.e("OKTM", "OKTM")
                    }, params = UpdateProgram.Params.toUpdate(program, exercisesToBeRemoved))
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