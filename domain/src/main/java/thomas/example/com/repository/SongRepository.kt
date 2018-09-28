package thomas.example.com.repository

import io.reactivex.Observable
import thomas.example.com.model.Song

interface SongRepository {

    fun createSong(song: Song): Observable<String>

    fun retrieveSongsListByUserId(idUser: String): Observable<List<Song>>

    fun retrieveSongById(idSong: String): Observable<Song>

    fun removeSong(idSong: String): Observable<Boolean>
}