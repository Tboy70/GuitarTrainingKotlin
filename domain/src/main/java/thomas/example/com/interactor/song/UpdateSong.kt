package thomas.example.com.interactor.song

import io.reactivex.Completable
import thomas.example.com.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.example.com.model.Song
import thomas.example.com.repository.SongRepository
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