package thomas.example.com.data.mapper

import thomas.example.com.data.entity.ScoreEntity
import thomas.example.com.model.Score
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreEntityDataMapper @Inject constructor() {

    fun transformEntityToModel(scoreEntity: ScoreEntity): Score {
        val score = Score()
        score.idScore = scoreEntity.idScore
        score.valueScore = scoreEntity.valueScore
        score.dateScore = scoreEntity.dateScore
        score.idSong = scoreEntity.idSong
        return score
    }

    fun transformListEntitiesToListModels(scoreEntityList: List<ScoreEntity>): List<Score> {
        val scoreList: MutableList<Score> = mutableListOf()

        for (scoreEntity: ScoreEntity in scoreEntityList) {
            scoreList.add(transformEntityToModel(scoreEntity))
        }
        return scoreList
    }

    fun transformModelToEntity(score: Score): ScoreEntity {
        val scoreEntity = ScoreEntity()
        scoreEntity.idScore = score.idScore
        scoreEntity.valueScore = score.valueScore
        scoreEntity.dateScore = score.dateScore
        scoreEntity.idSong = score.idSong
        return scoreEntity
    }

    fun transformListModelsToListEntities(scoreModelsList: List<Score>): List<ScoreEntity> {
        val scoreEntitiesList: MutableList<ScoreEntity> = mutableListOf()

        for (score: Score in scoreModelsList) {
            scoreEntitiesList.add(transformModelToEntity(score))
        }
        return scoreEntitiesList
    }
}