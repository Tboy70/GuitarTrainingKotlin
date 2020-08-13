package thomas.guitartrainingkotlin.domain.interactor.song

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject

class UpdateSong @Inject constructor(
    private val songRepository: SongRepository
) {

    fun updateSong(song: Song): Flow<Unit> {
        return songRepository.updateSong(song)
    }

}