package thomas.example.com.interactor.program

import io.reactivex.Completable
import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.base.UseCase
import thomas.example.com.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.example.com.model.Exercise
import thomas.example.com.model.Program
import thomas.example.com.repository.ProgramRepository
import javax.inject.Inject

class UpdateProgram @Inject constructor(
    private var programRepository: ProgramRepository
) : CompletableParametrizedUseCase<UpdateProgram.Params>() {

    override fun build(params: UpdateProgram.Params): Completable {
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