package thomas.example.com.interactor.user

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.Program
import thomas.example.com.repository.ProgramRepository
import javax.inject.Inject

class RetrieveProgramsListByUserId @Inject constructor(threadExecutor: ThreadExecutor,
                                                       private var programRepository: ProgramRepository)
    : UseCase<List<Program>, RetrieveProgramsListByUserId.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: Params): Observable<List<Program>> {
        return programRepository.retrieveProgramsListByUserId(params.userId)
    }

    class Params(val userId: String) {

        companion object {
            fun forList(userId: String): Params {
                return Params(userId)
            }
        }
    }
}