package thomas.guitartrainingkotlin.domain.interactor.song

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.ScoreFeedback
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject

class SendScoreFeedback @Inject constructor(
    private val songRepository: SongRepository
) {

    fun sendScoreFeedback(scoreFeedback: ScoreFeedback, idSong: String): Flow<Unit> {
        return songRepository.sendScoreFeedback(scoreFeedback, idSong)
    }

}