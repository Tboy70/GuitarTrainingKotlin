package thomas.example.com.guitarTrainingKotlin.viewmodel.program

import android.util.SparseArray
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.interactor.program.CreateProgram
import thomas.example.com.model.Exercise
import thomas.example.com.model.Program
import java.util.*
import javax.inject.Inject

class UserProgramCreationViewModel @Inject constructor(private var createProgram: CreateProgram) : ViewModel() {

    val creationProgramSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val creationProgramNotLaunch: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null

    fun checkInformationAndValidateCreation(nameProgram: String, descriptionProgram: String, exercises: SparseArray<String>, instrumentMode: String) {

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

            createProgram.execute(
                onComplete = {
                },
                onError = {
                    errorThrowable = it
                    creationProgramSuccess.postValue(false)
                },
                onNext = {
                    if (it) {
                        creationProgramSuccess.postValue(true)
                    }
                }, params = CreateProgram.Params.toCreate(program, exercisesList)
            )
        } else {
            creationProgramNotLaunch.postValue(true)
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
}