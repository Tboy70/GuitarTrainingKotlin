package thomas.example.com.interactor.song

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.Song
import thomas.example.com.repository.SongRepository
import javax.inject.Inject

class RetrieveSongById @Inject constructor(
    threadExecutor: ThreadExecutor,
    private var songRepository: SongRepository
) : UseCase<Song, RetrieveSongById.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: Params): Observable<Song> {
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