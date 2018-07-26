package thomas.example.com.interactor.program

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.Program
import thomas.example.com.repository.ProgramRepository
import javax.inject.Inject

class RetrieveProgramById @Inject constructor(threadExecutor: ThreadExecutor,
                                              private var programRepository: ProgramRepository)
    : UseCase<Program, RetrieveProgramById.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: Params): Observable<Program> {
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