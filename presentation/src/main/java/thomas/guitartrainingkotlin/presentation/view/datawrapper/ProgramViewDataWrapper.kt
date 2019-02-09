package thomas.guitartrainingkotlin.presentation.view.datawrapper

import thomas.guitartrainingkotlin.domain.model.Program

class ProgramViewDataWrapper(private val program: Program) {

    fun getId() = program.idProgram

    fun getName() = program.nameProgram

    fun getDescription() = program.descriptionProgram

    fun getExercises() = program.exercises
}