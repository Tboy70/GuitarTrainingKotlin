package thomas.example.com.interactor.user

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.Song
import thomas.example.com.repository.SongRepository
import javax.inject.Inject

class RetrieveSongsListByUserId @Inject constructor(
    threadExecutor: ThreadExecutor,
    private var songRepository: SongRepository
) : UseCase<List<Song>, RetrieveSongsListByUserId.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: Params): Observable<List<Song>> {
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