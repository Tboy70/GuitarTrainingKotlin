package thomas.guitartrainingkotlin.domain.interactor.program

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import javax.inject.Inject

class UpdateProgram @Inject constructor(
    private val programRepository: ProgramRepository
) {

    fun updateProgram(program: Program, exercisesToBeRemoved: MutableList<Exercise>): Flow<Unit> {
        return programRepository.updateProgram(program, exercisesToBeRemoved)
    }
}