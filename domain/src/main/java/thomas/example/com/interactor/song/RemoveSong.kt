package thomas.example.com.interactor.song

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.base.UseCase
import thomas.example.com.repository.SongRepository
import javax.inject.Inject

class RemoveSong @Inject constructor(
    threadExecutor: ThreadExecutor,
    private var songRepository: SongRepository
) : UseCase<Boolean, RemoveSong.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: RemoveSong.Params): Observable<Boolean> {
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