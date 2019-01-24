package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.ScoreEntity
import thomas.example.com.data.entity.remote.score.ScoreRemoteEntity
import thomas.example.com.data.exception.mapper.DataMappingException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreRemoteEntityDataMapper @Inject constructor() {

    @Throws(DataMappingException::class)
    fun transformToEntity(scoreRemoteEntity: ScoreRemoteEntity): ScoreEntity {
        try {
            return ScoreEntity(
                idScore = scoreRemoteEntity.idSong,
                valueScore = scoreRemoteEntity.valueScore,
                dateScore = scoreRemoteEntity.dateScore,
                idSong = scoreRemoteEntity.idSong
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToEntity(scoreRemoteEntityList: List<ScoreRemoteEntity>) = scoreRemoteEntityList.mapNotNull { scoreRemoteEntity ->
        try {
            transformToEntity(scoreRemoteEntity)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformFromEntity(scoreEntity: ScoreEntity): ScoreRemoteEntity {
        try {
            return ScoreRemoteEntity(
                idScore = scoreEntity.idSong,
                valueScore = scoreEntity.valueScore,
                dateScore = scoreEntity.dateScore,
                idSong = scoreEntity.idSong
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformFromEntity(scoreEntityList: List<ScoreEntity>) = scoreEntityList.mapNotNull { scoreEntity ->
        try {
            transformFromEntity(scoreEntity)
        } catch (e: DataMappingException) {
            null
        }
    }
}