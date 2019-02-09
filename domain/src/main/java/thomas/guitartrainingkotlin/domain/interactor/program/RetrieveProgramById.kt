package thomas.guitartrainingkotlin.domain.interactor.program

import io.reactivex.Single
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import javax.inject.Inject

class RetrieveProgramById @Inject constructor(
        private var programRepository: ProgramRepository
) : SingleParametrizedUseCase<Program, RetrieveProgramById.Params>() {

    override fun build(params: Params): Single<Program> {
        return programRepository.retrieveProgramById(params.idProgram)
    }

    class Params(val idProgram: String) {

        companion object {
            fun toRetrieve(idProgram: String): Params {
                return Params(idProgram)
            }
        }
    }
}