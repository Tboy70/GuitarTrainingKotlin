package thomas.guitartrainingkotlin.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import thomas.guitartrainingkotlin.domain.model.Score
import thomas.guitartrainingkotlin.domain.model.ScoreFeedback
import thomas.guitartrainingkotlin.domain.model.Song

interface SongRepository {

    fun createSong(song: Song): Completable

    fun retrieveSongListByUserId(userId: String): Single<List<Song>>

    fun retrieveSongById(idSong: String): Single<Song>

    fun updateSong(song: Song): Completable

    fun removeSong(idSong: String): Completable

    fun sendScoreFeedback(scoreFeedback: ScoreFeedback, idSong: String): Completable

    fun retrieveSongScoreHistory(idSong: String): Single<List<Score>>
}