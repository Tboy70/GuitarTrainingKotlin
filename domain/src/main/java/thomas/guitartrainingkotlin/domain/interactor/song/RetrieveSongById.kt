package thomas.guitartrainingkotlin.domain.interactor.song

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject

class RetrieveSongById @Inject constructor(
    private val songRepository: SongRepository
) {

    fun retrieveSongById(idSong: String): Flow<Song> {
        return songRepository.retrieveSongById(idSong)
    }

}