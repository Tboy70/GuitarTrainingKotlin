package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.ScoreFeedbackEntity
import thomas.example.com.data.entity.remote.song.ScoreFeedbackRemoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreFeedbackRemoteEntityDataMapper @Inject constructor() {

    fun transformRemoteEntityToEntity(scoreFeedbackRemoteEntity: ScoreFeedbackRemoteEntity): ScoreFeedbackEntity {
        val scoreFeedbackEntity = ScoreFeedbackEntity()
        scoreFeedbackEntity.scoreFeedback = scoreFeedbackRemoteEntity.scoreFeedback
        return scoreFeedbackEntity
    }

    fun transformEntityToRemoteEntity(scoreFeedbackEntity: ScoreFeedbackEntity): ScoreFeedbackRemoteEntity {
        val scoreFeedbackRemoteEntity = ScoreFeedbackRemoteEntity()
        scoreFeedbackRemoteEntity.scoreFeedback = scoreFeedbackEntity.scoreFeedback
        return scoreFeedbackRemoteEntity
    }
}