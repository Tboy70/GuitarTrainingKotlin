package thomas.example.com.data.mapper

import thomas.example.com.data.entity.SongEntity
import thomas.example.com.data.exception.mapper.DataMappingException
import thomas.example.com.model.Song
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongEntityDataMapper @Inject constructor() {

    @Throws(DataMappingException::class)
    fun transformFromEntity(songEntity: SongEntity): Song {
        try {
            return Song(
                idSong = songEntity.idSong,
                titleSong = songEntity.titleSong,
                artistSong = songEntity.artistSong,
                averageScoreSong = songEntity.averageScoreSong,
                totalScoreSong = songEntity.totalScoreSong,
                nbPlay = songEntity.nbPlay,
                lastPlay = songEntity.lastPlay,
                userId = songEntity.userId,
                idInstrument = songEntity.idInstrument
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformFromEntity(songEntityList: List<SongEntity>) = songEntityList.mapNotNull { songEntity ->
        try {
            transformFromEntity(songEntity)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformToEntity(song: Song): SongEntity {
        try {
            return SongEntity(
                idSong = song.idSong,
                titleSong = song.titleSong,
                artistSong = song.artistSong,
                averageScoreSong = song.averageScoreSong,
                totalScoreSong = song.totalScoreSong,
                nbPlay = song.nbPlay,
                lastPlay = song.lastPlay,
                userId = song.userId,
                idInstrument = song.idInstrument
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToEntity(songList: List<Song>) = songList.mapNotNull { song ->
        try {
            transformToEntity(song)
        } catch (e: DataMappingException) {
            null
        }
    }
}