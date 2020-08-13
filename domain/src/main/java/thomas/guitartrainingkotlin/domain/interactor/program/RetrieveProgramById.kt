package thomas.guitartrainingkotlin.domain.interactor.program

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import javax.inject.Inject

class RetrieveProgramById @Inject constructor(
    private val programRepository: ProgramRepository
) {

    fun retrieveProgramById(idProgram: String): Flow<Program> {
        return programRepository.retrieveProgramById(idProgram)
    }
}