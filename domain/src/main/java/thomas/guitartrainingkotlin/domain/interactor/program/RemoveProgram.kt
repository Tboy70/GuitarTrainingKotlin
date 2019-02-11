package thomas.guitartrainingkotlin.domain.interactor.program

import io.reactivex.Completable
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import javax.inject.Inject

class RemoveProgram @Inject constructor(
        private val programRepository: ProgramRepository
) : CompletableParametrizedUseCase<RemoveProgram.Params>() {

    override fun build(params: RemoveProgram.Params): Completable {
        return programRepository.removeProgram(params.idProgram)
    }

    class Params(val idProgram: String) {

        companion object {
            fun toRemove(idProgram: String): Params {
                return Params(idProgram)
            }
        }
    }
}