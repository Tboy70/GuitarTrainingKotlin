package thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper

import androidx.annotation.Nullable
import thomas.example.com.model.Exercise
import thomas.example.com.model.Program
import java.io.Serializable

class ProgramViewDataWrapper(@Transient private val program: Program) : Serializable {

    fun getId(): String {
        return program.idProgram
    }

    fun getName(): String {
        return program.nameProgram
    }

    @Nullable
    fun getDescription(): String {
        return program.descriptionProgram
    }

    @Nullable
    fun getExercises() : MutableList<Exercise> {
        return program.exercises
    }
}