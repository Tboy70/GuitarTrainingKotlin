package thomas.guitartrainingkotlin.domain.interactor.song

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.Score
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject

class RetrieveSongScoreHistory @Inject constructor(
    private val songRepository: SongRepository
) {

    fun retrieveSongScoreHistory(idSong: String): Flow<List<Score>> {
        return songRepository.retrieveSongScoreHistory(idSong)
    }

}