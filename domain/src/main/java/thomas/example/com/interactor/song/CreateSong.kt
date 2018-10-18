package thomas.example.com.interactor.song

import io.reactivex.Completable
import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.base.UseCase
import thomas.example.com.interactor.base.parametrized.CompletableParametrizedUseCase
import thomas.example.com.model.Song
import thomas.example.com.repository.SongRepository
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class CreateSong @Inject constructor(
    private var songRepository: SongRepository,
    private var userRepository: UserRepository
) :
        CompletableParametrizedUseCase<CreateSong.Params>() {

    override fun build(params: CreateSong.Params): Completable {
        return userRepository.getIdUserInSharedPrefs().map {
            params.song.idUser = it
        }.flatMapCompletable {
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