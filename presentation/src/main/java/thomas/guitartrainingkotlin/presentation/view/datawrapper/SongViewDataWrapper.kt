package thomas.guitartrainingkotlin.presentation.view.datawrapper

import thomas.guitartrainingkotlin.domain.model.Song
import java.io.Serializable

class SongViewDataWrapper(@Transient private val song: Song) : Serializable {

    fun getId(): String = song.idSong

    fun getTitleSong() : String = song.titleSong

    fun getArtistSong() : String  = song.artistSong

    fun getAverageScoreSong(): Float = song.averageScoreSong

    fun getNbPlay(): Int = song.nbPlay

    fun getLastPlay(): String = song.lastPlay
}