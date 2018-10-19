package thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper

import thomas.example.com.model.Song
import java.io.Serializable

class SongViewDataWrapper(@Transient private val song: Song) : Serializable {

    fun getIdSong(): String {
        return song.idSong
    }

    fun getTitleSong() : String {
        return song.titleSong
    }

    fun getArtistSong() : String {
        return song.artistSong
    }

    fun getAverageScoreSong(): Float {
        return song.averageScoreSong
    }

    fun getNbPlay(): Int {
        return song.nbPlay
    }

    fun getLastPlay(): String {
        return song.lastPlay
    }
}