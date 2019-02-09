package thomas.guitartrainingkotlin.domain.interactor.program

import io.reactivex.Completable
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
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