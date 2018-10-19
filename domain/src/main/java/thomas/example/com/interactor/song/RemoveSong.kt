package thomas.example.com.interactor.song

import io.reactivex.Completable
import thomas.example.com.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.example.com.repository.SongRepository
import javax.inject.Inject

class RemoveSong @Inject constructor(
        private var songRepository: SongRepository
) : CompletableParametrizedUseCase<RemoveSong.Params>() {

    override fun build(params: RemoveSong.Params): Completable {
        return songRepository.removeSong(params.idSong)
    }

    class Params(val idSong: String) {

        companion object {
            fun toRemove(idSong: String): Params {
                return Params(idSong)
            }
        }
    }
}