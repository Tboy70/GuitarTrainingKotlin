package thomas.guitartrainingkotlin.domain.interactor.user

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject

class RetrieveSongListByUserId @Inject constructor(
    private val songRepository: SongRepository
)  {

    fun retrieveSongListByUserId(userId: String): Flow<List<Song>> {
        return songRepository.retrieveSongListByUserId(userId)
    }
}