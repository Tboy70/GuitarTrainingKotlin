package thomas.example.com.interactor.song

import io.reactivex.Single
import thomas.example.com.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.example.com.model.Song
import thomas.example.com.repository.SongRepository
import javax.inject.Inject

class RetrieveSongById @Inject constructor(
        private var songRepository: SongRepository
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