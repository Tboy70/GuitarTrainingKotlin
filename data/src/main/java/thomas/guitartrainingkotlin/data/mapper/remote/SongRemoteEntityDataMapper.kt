package thomas.guitartrainingkotlin.data.mapper.remote

import thomas.guitartrainingkotlin.data.entity.SongEntity
import thomas.guitartrainingkotlin.data.entity.remote.song.SongRemoteEntity
import thomas.guitartrainingkotlin.data.exception.mapper.DataMappingException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongRemoteEntityDataMapper @Inject constructor() {

    @Throws(DataMappingException::class)
    fun transformToEntity(songRemoteEntity: SongRemoteEntity): SongEntity {
        try {
            return SongEntity(
                idSong = songRemoteEntity.idSong,
                titleSong = songRemoteEntity.titleSong,
                artistSong = songRemoteEntity.artistSong,
                averageScoreSong = songRemoteEntity.averageScoreSong,
                totalScoreSong = songRemoteEntity.totalScoreSong,
                nbPlay = songRemoteEntity.nbPlay,
                lastPlay = songRemoteEntity.lastPlay,
                userId = songRemoteEntity.userId,
                idInstrument = songRemoteEntity.idInstrument
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToEntity(songRemoteEntityList: List<SongRemoteEntity>) =
        songRemoteEntityList.mapNotNull { songRemoteEntity ->
            try {
                transformToEntity(songRemoteEntity)
            } catch (e: DataMappingException) {
                null
            }
        }

    @Throws(DataMappingException::class)
    fun transformFromEntity(songEntity: SongEntity): SongRemoteEntity {
        try {
            return SongRemoteEntity(
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
}