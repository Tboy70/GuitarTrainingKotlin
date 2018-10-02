package thomas.example.com.repository

import io.reactivex.Observable
import thomas.example.com.model.Score
import thomas.example.com.model.ScoreFeedback
import thomas.example.com.model.Song

interface SongRepository {

    fun createSong(song: Song): Observable<String>

    fun retrieveSongsListByUserId(idUser: String): Observable<List<Song>>

    fun retrieveSongById(idSong: String): Observable<Song>

    fun updateSong(song: Song): Observable<Boolean>

    fun removeSong(idSong: String): Observable<Boolean>

    fun sendScoreFeedback(scoreFeedback: ScoreFeedback, idSong: String): Observable<Boolean>

    fun retrieveSongScoreHistoric(idSong: String): Observable<List<Score>>
}