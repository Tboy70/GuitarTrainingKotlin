package thomas.guitartrainingkotlin.data.mapper.db

import thomas.guitartrainingkotlin.data.entity.ScoreEntity
import thomas.guitartrainingkotlin.data.entity.db.ScoreDBEntity
import thomas.guitartrainingkotlin.data.exception.mapper.DataMappingException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreDBEntityDataMapper @Inject constructor() {

    fun transformFromDB(scoreDBEntityList: List<ScoreDBEntity>) =
        scoreDBEntityList.mapNotNull { scoreDBEntity ->
            try {
                transformFromDB(scoreDBEntity)
            } catch (e: DataMappingException) {
                null
            }
        }

    @Throws(DataMappingException::class)
    fun transformFromDB(scoreDBEntity: ScoreDBEntity): ScoreEntity {
        try {
            return ScoreEntity(
                idSong = scoreDBEntity.idSong,
                idScore = scoreDBEntity.idScore,
                dateScore = scoreDBEntity.dateScore,
                valueScore = scoreDBEntity.valueScore
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToDB(scoreEntityList: List<ScoreEntity>) = scoreEntityList.mapNotNull { scoreEntity ->
        try {
            transformToDB(scoreEntity)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformToDB(scoreEntity: ScoreEntity): ScoreDBEntity {
        try {
            return ScoreDBEntity(
                idSong = scoreEntity.idSong,
                idScore = scoreEntity.idScore,
                dateScore = scoreEntity.dateScore,
                valueScore = scoreEntity.valueScore
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }
}