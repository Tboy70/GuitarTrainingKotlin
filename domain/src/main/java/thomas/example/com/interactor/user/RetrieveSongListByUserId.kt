package thomas.example.com.interactor.user

import io.reactivex.Single
import thomas.example.com.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.example.com.model.Song
import thomas.example.com.repository.SongRepository
import javax.inject.Inject

class RetrieveSongListByUserId @Inject constructor(
        private var songRepository: SongRepository
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