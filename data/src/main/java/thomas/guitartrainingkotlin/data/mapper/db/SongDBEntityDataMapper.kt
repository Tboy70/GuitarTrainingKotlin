package thomas.guitartrainingkotlin.data.mapper.db

import thomas.guitartrainingkotlin.data.entity.SongEntity
import thomas.guitartrainingkotlin.data.entity.db.SongDBEntity
import thomas.guitartrainingkotlin.data.exception.mapper.DataMappingException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongDBEntityDataMapper @Inject constructor() {

    fun transformFromDB(songDBEntityList: List<SongDBEntity>) = songDBEntityList.mapNotNull { songDBEntity ->
        try {
            transformFromDB(songDBEntity)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformFromDB(songDBEntity: SongDBEntity): SongEntity {
        try {
            return SongEntity(
                idSong = songDBEntity.idSong,
                userId = songDBEntity.userId,
                nbPlay = songDBEntity.nbPlay,
                lastPlay = songDBEntity.lastPlay,
                titleSong = songDBEntity.titleSong,
                artistSong = songDBEntity.artistSong,
                idInstrument = songDBEntity.idInstrument,
                totalScoreSong = songDBEntity.totalScoreSong,
                averageScoreSong = songDBEntity.averageScoreSong
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToDB(songEntityList: List<SongEntity>) = songEntityList.mapNotNull { songEntity ->
        try {
            transformToDB(songEntity)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformToDB(songEntity: SongEntity): SongDBEntity {
        try {
            return SongDBEntity(
                idSong = songEntity.idSong,
                userId = songEntity.userId,
                nbPlay = songEntity.nbPlay,
                lastPlay = songEntity.lastPlay,
                titleSong = songEntity.titleSong,
                artistSong = songEntity.artistSong,
                idInstrument = songEntity.idInstrument,
                totalScoreSong = songEntity.totalScoreSong,
                averageScoreSong = songEntity.averageScoreSong
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }
}