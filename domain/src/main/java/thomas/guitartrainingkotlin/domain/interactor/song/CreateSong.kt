package thomas.guitartrainingkotlin.domain.interactor.song

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject

class CreateSong @Inject constructor(
    private val songRepository: SongRepository
) {

    fun createSong(songToCreate: Song): Flow<Unit> {
        return songRepository.createSong(songToCreate)
    }
}