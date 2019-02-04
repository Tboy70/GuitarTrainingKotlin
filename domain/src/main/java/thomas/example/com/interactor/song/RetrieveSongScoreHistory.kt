package thomas.example.com.interactor.song

import io.reactivex.Single
import thomas.example.com.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.example.com.model.Score
import thomas.example.com.repository.SongRepository
import javax.inject.Inject

class RetrieveSongScoreHistory @Inject constructor(
        private var songRepository: SongRepository
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