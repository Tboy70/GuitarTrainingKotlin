package thomas.example.com.interactor.program

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.Exercise
import thomas.example.com.model.Program
import thomas.example.com.repository.ProgramRepository
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class CreateProgram @Inject constructor(threadExecutor: ThreadExecutor,
                                        private var programRepository: ProgramRepository,
                                        private var userRepository: UserRepository)
    : UseCase<Boolean, CreateProgram.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: CreateProgram.Params): Observable<Boolean> {
        return userRepository.getIdUserInSharedPrefs().map {
            params.program.idUser = it
        }.flatMap {
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