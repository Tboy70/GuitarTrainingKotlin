package thomas.example.com.guitarTrainingKotlin.view.datawrapper

import thomas.example.com.model.Program

class ProgramViewDataWrapper(private val program: Program) {

    fun getId() = program.idProgram

    fun getName() = program.nameProgram

    fun getDescription() = program.descriptionProgram

    fun getExercises() = program.exercises
}