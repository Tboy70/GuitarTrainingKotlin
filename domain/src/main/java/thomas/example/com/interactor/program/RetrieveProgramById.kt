package thomas.example.com.interactor.program

import io.reactivex.Single
import thomas.example.com.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.example.com.model.Program
import thomas.example.com.repository.ProgramRepository
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