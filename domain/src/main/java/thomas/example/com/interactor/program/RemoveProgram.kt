package thomas.example.com.interactor.program

import io.reactivex.Completable
import thomas.example.com.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.example.com.repository.ProgramRepository
import javax.inject.Inject

class RemoveProgram @Inject constructor(
        private var programRepository: ProgramRepository
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