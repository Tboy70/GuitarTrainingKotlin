package thomas.guitartrainingkotlin.domain.interactor.song

import io.reactivex.Completable
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.guitartrainingkotlin.domain.model.ScoreFeedback
import thomas.guitartrainingkotlin.domain.repository.SongRepository
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