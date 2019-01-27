package thomas.example.com.interactor.program

import io.reactivex.Completable
import thomas.example.com.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.example.com.model.Exercise
import thomas.example.com.model.Program
import thomas.example.com.repository.ProgramRepository
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class CreateProgram @Inject constructor(
        private val programRepository: ProgramRepository,
        private val userRepository: UserRepository
) : CompletableParametrizedUseCase<CreateProgram.Params>() {

    override fun build(params: Params): Completable {
        return userRepository.getUserIdInSharedPrefs().map {
            params.program.userId = it
        }.flatMapCompletable {
            programRepository.createProgram(params.program, params.exercisesList)
        }
    }

    class Params(val program: Program, val exercisesList: List<Exercise>) {

        companion object {
            fun toCreate(program: Program, exercisesList: List<Exercise>): Params {
                return Params(program, exercisesList)
            }
        }
    }
}