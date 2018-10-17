package thomas.example.com.interactor.song

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.ScoreFeedback
import thomas.example.com.repository.SongRepository
import javax.inject.Inject

class SendScoreFeedback @Inject constructor(
    threadExecutor: ThreadExecutor,
    private var songRepository: SongRepository
) : UseCase<Boolean, SendScoreFeedback.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: SendScoreFeedback.Params): Observable<Boolean> {
        return songRepository.sendScoreFeedback(params.scoreFeedback, params.idSong)
    }

    class Params(val scoreFeedback: ScoreFeedback, val idSong: String) {

        companion object {
            fun toSend(scoreFeedback: ScoreFeedback, idSong: String): Params {
                return Params(scoreFeedback, idSong)
            }
        }
    }
}