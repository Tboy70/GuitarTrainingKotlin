package thomas.guitartrainingkotlin.domain.interactor.song

import io.reactivex.Completable
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

class CreateSong @Inject constructor(
    private var songRepository: SongRepository,
    private var userRepository: UserRepository
) : CompletableParametrizedUseCase<CreateSong.Params>() {

    override fun build(params: CreateSong.Params): Completable {
        return songRepository.createSong(params.song)
    }

    class Params(val song: Song) {

        companion object {
            fun toCreate(song: Song): Params {
                return Params(song)
            }
        }
    }
}