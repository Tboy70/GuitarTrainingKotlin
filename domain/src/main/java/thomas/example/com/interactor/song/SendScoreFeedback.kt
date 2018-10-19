package thomas.example.com.interactor.song

import io.reactivex.Completable
import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.base.UseCase
import thomas.example.com.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.example.com.model.ScoreFeedback
import thomas.example.com.repository.SongRepository
import javax.inject.Inject

class SendScoreFeedback @Inject constructor(
    private var songRepository: SongRepository
) : CompletableParametrizedUseCase<SendScoreFeedback.Params>() {

    override fun build(params: SendScoreFeedback.Params): Completable {
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