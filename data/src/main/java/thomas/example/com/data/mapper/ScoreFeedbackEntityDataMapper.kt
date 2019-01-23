package thomas.example.com.data.mapper

import thomas.example.com.data.entity.ScoreFeedbackEntity
import thomas.example.com.data.exception.mapper.DataMappingException
import thomas.example.com.model.ScoreFeedback
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreFeedbackEntityDataMapper @Inject constructor() {

    @Throws(DataMappingException::class)
    fun transformFromEntity(scoreFeedbackEntity: ScoreFeedbackEntity): ScoreFeedback {
        try {
            return ScoreFeedback(
                scoreFeedback = scoreFeedbackEntity.scoreFeedback
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    @Throws(DataMappingException::class)
    fun transformModelToEntity(scoreFeedback: ScoreFeedback): ScoreFeedbackEntity {
        try {
            return ScoreFeedbackEntity(
                scoreFeedback = scoreFeedback.scoreFeedback
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }
}