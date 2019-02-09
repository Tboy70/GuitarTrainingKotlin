package thomas.guitartrainingkotlin.data.mapper

import thomas.guitartrainingkotlin.data.entity.ScoreFeedbackEntity
import thomas.guitartrainingkotlin.data.exception.mapper.DataMappingException
import thomas.guitartrainingkotlin.domain.model.ScoreFeedback
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