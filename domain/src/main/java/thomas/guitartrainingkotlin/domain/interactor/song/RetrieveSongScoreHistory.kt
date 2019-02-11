package thomas.guitartrainingkotlin.domain.interactor.song

import io.reactivex.Single
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.guitartrainingkotlin.domain.model.Score
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject

class RetrieveSongScoreHistory @Inject constructor(
        private val songRepository: SongRepository
) : SingleParametrizedUseCase<List<Score>, RetrieveSongScoreHistory.Params>() {

    override fun build(params: Params): Single<List<Score>> {
        return songRepository.retrieveSongScoreHistory(params.idSong)
    }

    class Params(val idSong: String) {

        companion object {
            fun toRetrieve(idSong: String): Params {
                return Params(idSong)
            }
        }
    }
}