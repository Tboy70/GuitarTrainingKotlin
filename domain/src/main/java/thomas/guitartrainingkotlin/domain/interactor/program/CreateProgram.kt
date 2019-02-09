package thomas.guitartrainingkotlin.domain.interactor.program

import io.reactivex.Completable
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class CreateProgram @Inject constructor(
        private val programRepository: ProgramRepository,
        private val userRepository: UserRepository
) : CompletableParametrizedUseCase<CreateProgram.Params>() {

    override fun build(params: Params): Completable {
        return userRepository.retrieveUserIdInSharedPrefs().map {
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