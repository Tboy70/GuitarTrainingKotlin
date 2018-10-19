package thomas.example.com.interactor.user

import io.reactivex.Single
import thomas.example.com.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.example.com.model.Song
import thomas.example.com.repository.SongRepository
import javax.inject.Inject

class RetrieveSongsListByUserId @Inject constructor(
        private var songRepository: SongRepository
) : SingleParametrizedUseCase<List<Song>, RetrieveSongsListByUserId.Params>() {

    override fun build(params: Params): Single<List<Song>> {
        return songRepository.retrieveSongsListByUserId(params.idUser)
    }

    class Params(val idUser: String) {

        companion object {
            fun forList(idUser: String): Params {
                return Params(idUser)
            }
        }
    }
}