package thomas.guitartrainingkotlin.domain.repository

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program

interface ProgramRepository {

    fun retrieveProgramListByUserId(userId: String): Flow<List<Program>>

    fun retrieveProgramById(idProgram: String): Flow<Program>

    fun createProgram(program: Program, exercisesList: List<Exercise>): Flow<Unit?>

    fun updateProgram(program: Program, exercisesToBeRemoved: List<Exercise>): Flow<Unit>

    fun removeProgram(idProgram: String): Flow<Unit>
}