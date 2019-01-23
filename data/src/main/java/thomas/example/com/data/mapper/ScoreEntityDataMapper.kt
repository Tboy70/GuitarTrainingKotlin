package thomas.example.com.data.mapper

import thomas.example.com.data.entity.ScoreEntity
import thomas.example.com.data.exception.mapper.DataMappingException
import thomas.example.com.model.Score
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreEntityDataMapper @Inject constructor() {

    @Throws(DataMappingException::class)
    fun transformFromEntity(scoreEntity: ScoreEntity): Score {
        try {
            return Score(
                idScore = scoreEntity.idScore,
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

    @Throws(DataMappingException::class)
    fun transformToEntity(score: Score): ScoreEntity {
        try {
            return ScoreEntity(
                idScore = score.idScore,
                valueScore = score.valueScore,
                dateScore = score.dateScore,
                idSong = score.idSong
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToEntity(scoreList: List<Score>) = scoreList.mapNotNull { score ->
        try {
            transformToEntity(score)
        } catch (e: DataMappingException) {
            null
        }
    }
}