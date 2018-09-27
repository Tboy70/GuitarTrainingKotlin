package thomas.example.com.interactor.song

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.Song
import thomas.example.com.repository.SongRepository
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class CreateSong @Inject constructor(threadExecutor: ThreadExecutor,
                                     private var songRepository: SongRepository,
                                     private var userRepository: UserRepository) :
        UseCase<String, CreateSong.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: CreateSong.Params): Observable<String> {
        return userRepository.getIdUserInSharedPrefs().map {
            params.song.idUser = it
        }.flatMap {
            songRepository.createSong(params.song)
        }
    }

    class Params(val song: Song) {

        companion object {
            fun toCreate(song: Song): Params {
                return Params(song)
            }
        }
    }
}