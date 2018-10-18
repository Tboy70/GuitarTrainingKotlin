package thomas.example.com.interactor.song

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.base.UseCase
import thomas.example.com.model.Score
import thomas.example.com.repository.SongRepository
import javax.inject.Inject

class RetrieveSongScoreHistoric @Inject constructor(
    threadExecutor: ThreadExecutor,
    private var songRepository: SongRepository
) : UseCase<List<Score>, RetrieveSongScoreHistoric.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: Params): Observable<List<Score>> {
        return songRepository.retrieveSongScoreHistoric(params.idSong)
    }

    class Params(val idSong: String) {

        companion object {
            fun toRetrieve(idSong: String): Params {
                return Params(idSong)
            }
        }
    }
}