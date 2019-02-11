package thomas.guitartrainingkotlin.domain.interactor.user

import io.reactivex.Single
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject

class RetrieveSongListByUserId @Inject constructor(
        private val songRepository: SongRepository
) : SingleParametrizedUseCase<List<Song>, RetrieveSongListByUserId.Params>() {

    override fun build(params: Params): Single<List<Song>> {
        return songRepository.retrieveSongListByUserId(params.userId)
    }

    class Params(val userId: String) {

        companion object {
            fun forList(userId: String): Params {
                return Params(userId)
            }
        }
    }
}