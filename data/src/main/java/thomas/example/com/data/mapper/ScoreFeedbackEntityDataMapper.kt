package thomas.example.com.data.mapper

import thomas.example.com.data.entity.ScoreFeedbackEntity
import thomas.example.com.model.ScoreFeedback
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreFeedbackEntityDataMapper @Inject constructor() {

    fun transformEntityToModel(scoreFeedbackEntity: ScoreFeedbackEntity): ScoreFeedback {
        val scoreFeedback = ScoreFeedback()
        scoreFeedback.scoreFeedback = scoreFeedbackEntity.scoreFeedback
        return scoreFeedback
    }


    fun transformModelToEntity(scoreFeedback: ScoreFeedback): ScoreFeedbackEntity {
        val scoreFeedbackEntity = ScoreFeedbackEntity()
        scoreFeedbackEntity.scoreFeedback = scoreFeedback.scoreFeedback
        return scoreFeedbackEntity
    }
}