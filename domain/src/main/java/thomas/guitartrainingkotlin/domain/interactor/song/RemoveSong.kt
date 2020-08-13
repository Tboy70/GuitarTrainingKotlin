package thomas.guitartrainingkotlin.domain.interactor.song

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject

class RemoveSong @Inject constructor(
    private val songRepository: SongRepository
) {

    fun removeSong(idSong: String): Flow<Unit> {
        return songRepository.removeSong(idSong)
    }
}