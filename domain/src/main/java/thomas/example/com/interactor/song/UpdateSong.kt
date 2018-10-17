package thomas.example.com.interactor.song

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.Song
import thomas.example.com.repository.SongRepository
import javax.inject.Inject

class UpdateSong @Inject constructor(
    threadExecutor: ThreadExecutor,
    private var songRepository: SongRepository
) : UseCase<Boolean, UpdateSong.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: UpdateSong.Params): Observable<Boolean> {
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