package thomas.example.com.guitarTrainingKotlin.view.datawrapper

import thomas.example.com.model.Song
import java.io.Serializable

class SongViewDataWrapper(@Transient private val song: Song) : Serializable {

    fun getId(): String = song.idSong

    fun getTitleSong() : String = song.titleSong

    fun getArtistSong() : String  = song.artistSong

    fun getAverageScoreSong(): Float = song.averageScoreSong

    fun getNbPlay(): Int = song.nbPlay

    fun getLastPlay(): String = song.lastPlay
}