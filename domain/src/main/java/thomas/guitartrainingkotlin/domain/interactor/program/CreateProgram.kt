package thomas.guitartrainingkotlin.domain.interactor.program

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

@ExperimentalCoroutinesApi
class CreateProgram @Inject constructor(
    private val userRepository: UserRepository,
    private val programRepository: ProgramRepository
) {

    fun createProgram(program: Program, exercisesList: List<Exercise>): Flow<Unit?> {
        return userRepository.retrieveUserIdInSharedPrefs().map {
            program.userId = it
        }.onCompletion {
            programRepository.createProgram(program, exercisesList)
        }
    }
}