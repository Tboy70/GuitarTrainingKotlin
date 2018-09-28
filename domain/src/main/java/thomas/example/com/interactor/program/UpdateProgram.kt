package thomas.example.com.interactor.program

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.Exercise
import thomas.example.com.model.Program
import thomas.example.com.repository.ProgramRepository
import javax.inject.Inject

class UpdateProgram @Inject constructor(threadExecutor: ThreadExecutor,
                                        private var programRepository: ProgramRepository)
    : UseCase<Boolean, UpdateProgram.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: UpdateProgram.Params): Observable<Boolean> {
        return programRepository.updateProgram(params.program, params.exercisesToBeRemoved)
    }

    class Params(val program: Program, val exercisesToBeRemoved: List<Exercise>) {

        companion object {
            fun toUpdate(program: Program, exercisesToBeRemoved: List<Exercise>): Params {
                return Params(program, exercisesToBeRemoved)
            }
        }
    }
}