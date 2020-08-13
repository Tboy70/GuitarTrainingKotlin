package thomas.guitartrainingkotlin.domain.interactor.program

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import javax.inject.Inject

class RemoveProgram @Inject constructor(
    private val programRepository: ProgramRepository
) {

    fun removeProgram(idProgram: String): Flow<Unit> {
        return programRepository.removeProgram(idProgram)
    }
}