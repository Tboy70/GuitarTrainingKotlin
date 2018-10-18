package thomas.example.com.interactor.program

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.base.UseCase
import thomas.example.com.repository.ProgramRepository
import javax.inject.Inject

class RemoveProgram @Inject constructor(
    threadExecutor: ThreadExecutor,
    private var programRepository: ProgramRepository
) : UseCase<Boolean, RemoveProgram.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: RemoveProgram.Params): Observable<Boolean> {
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