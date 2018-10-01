package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.ScoreEntity
import thomas.example.com.data.entity.remote.score.ScoreRemoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreRemoteEntityDataMapper @Inject constructor() {

    fun transformRemoteEntityToEntity(scoreRemoteEntity: ScoreRemoteEntity): ScoreEntity {
        val scoreEntity = ScoreEntity()
        scoreEntity.idScore = scoreRemoteEntity.idSong
        scoreEntity.valueScore = scoreRemoteEntity.valueScore
        scoreEntity.dateScore = scoreRemoteEntity.dateScore
        scoreEntity.idSong = scoreRemoteEntity.idSong

        return scoreEntity
    }

    fun transformListRemoteEntitiesToListEntities(scoreRemoteEntitiesList: List<ScoreRemoteEntity>): List<ScoreEntity> {
        val scoreEntitiesList: MutableList<ScoreEntity> = mutableListOf()

        for (scoreRemoteEntity: ScoreRemoteEntity in scoreRemoteEntitiesList) {
            scoreEntitiesList.add(transformRemoteEntityToEntity(scoreRemoteEntity))
        }
        return scoreEntitiesList
    }

    fun transformEntityToRemoteEntity(scoreEntity: ScoreEntity): ScoreRemoteEntity {
        val scoreRemoteEntity = ScoreRemoteEntity()
        scoreRemoteEntity.idScore = scoreEntity.idScore
        scoreRemoteEntity.valueScore = scoreEntity.valueScore
        scoreRemoteEntity.dateScore = scoreEntity.dateScore
        scoreRemoteEntity.idSong = scoreEntity.idSong

        return scoreRemoteEntity
    }

    fun transformListEntitiesToListRemoteEntities(scoreEntitiesList: List<ScoreEntity>): List<ScoreRemoteEntity> {
        val scoreRemoteEntitiesList: MutableList<ScoreRemoteEntity> = mutableListOf()

        for (scoreEntity: ScoreEntity in scoreEntitiesList) {
            scoreRemoteEntitiesList.add(transformEntityToRemoteEntity(scoreEntity))
        }
        return scoreRemoteEntitiesList
    }
}