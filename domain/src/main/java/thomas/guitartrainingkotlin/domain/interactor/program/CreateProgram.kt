package thomas.guitartrainingkotlin.domain.interactor.program

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class CreateProgram @Inject constructor(
    private val userRepository: UserRepository,
    private val programRepository: ProgramRepository
) {

    suspend fun createProgram(program: Program, exercisesList: List<Exercise>): Flow<Unit> {
        return userRepository.retrieveUserId().map {
            program.userId = it
        }.flatMapMerge {
            programRepository.createProgram(program, exercisesList)
        }
    }
}