package thomas.guitartrainingkotlin.domain.interactor.song

import io.reactivex.Completable
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject

class UpdateSong @Inject constructor(
    private var songRepository: SongRepository
) : CompletableParametrizedUseCase<UpdateSong.Params>() {

    override fun build(params: UpdateSong.Params): Completable {
        return songRepository.updateSong(params.song)
    }

    class Params(val song: Song) {

        companion object {
            fun toUpdate(song: Song): Params {
                return Params(song)
            }
        }
    }
}