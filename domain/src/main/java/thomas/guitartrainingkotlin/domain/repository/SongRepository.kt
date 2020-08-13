package thomas.guitartrainingkotlin.domain.repository

import kotlinx.coroutines.flow.Flow
import thomas.guitartrainingkotlin.domain.model.Score
import thomas.guitartrainingkotlin.domain.model.ScoreFeedback
import thomas.guitartrainingkotlin.domain.model.Song

interface SongRepository {

    fun createSong(song: Song): Flow<Unit>

    fun retrieveSongListByUserId(userId: String): Flow<List<Song>>

    fun retrieveSongById(idSong: String): Flow<Song>

    fun updateSong(song: Song): Flow<Unit>

    fun removeSong(idSong: String): Flow<Unit>

    fun sendScoreFeedback(scoreFeedback: ScoreFeedback, idSong: String): Flow<Unit>

    fun retrieveSongScoreHistory(idSong: String): Flow<List<Score>>
}