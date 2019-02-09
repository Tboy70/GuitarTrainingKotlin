package thomas.guitartrainingkotlin.domain.interactor.song

import io.reactivex.Completable
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.guitartrainingkotlin.domain.repository.SongRepository
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