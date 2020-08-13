package thomas.guitartrainingkotlin.domain.interactor.user

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import javax.inject.Inject

class RetrieveProgramsListByUserId @Inject constructor(
    private val programRepository: ProgramRepository
) {

    fun retrieveProgramsListByUserId(userId: String): Flow<List<Program>> {
        return programRepository.retrieveProgramListByUserId(userId)
    }

}