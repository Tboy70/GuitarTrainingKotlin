package thomas.example.com.interactor.user

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.Song
import thomas.example.com.repository.ProgramRepository
import javax.inject.Inject

class RetrieveSongsListByUserId @Inject constructor(threadExecutor: ThreadExecutor,
                                                    private var programRepository: ProgramRepository)
    : UseCase<List<Song>, RetrieveSongsListByUserId.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: Params): Observable<List<Song>> {
        return Observable.error(NullPointerException())
    }

    class Params(val userId: String) {

        companion object {
            fun forList(userId: String): Params {
                return Params(userId)
            }
        }
    }
}