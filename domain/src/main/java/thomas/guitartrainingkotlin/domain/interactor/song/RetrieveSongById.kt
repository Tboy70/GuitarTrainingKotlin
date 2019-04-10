package thomas.guitartrainingkotlin.domain.interactor.song

import io.reactivex.Single
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject

class RetrieveSongById @Inject constructor(
    private val songRepository: SongRepository
) : SingleParametrizedUseCase<Song, RetrieveSongById.Params>() {

    override fun build(params: Params): Single<Song> {
        return songRepository.retrieveSongById(params.idSong)
    }

    class Params(val idSong: String) {

        companion object {
            fun toRetrieve(idSong: String): Params {
                return Params(idSong)
            }
        }
    }
}