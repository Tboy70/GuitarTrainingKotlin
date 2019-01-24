package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.ScoreFeedbackEntity
import thomas.example.com.data.entity.remote.song.ScoreFeedbackRemoteEntity
import thomas.example.com.data.exception.mapper.DataMappingException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreFeedbackRemoteEntityDataMapper @Inject constructor() {

    @Throws(DataMappingException::class)
    fun transformToEntity(scoreFeedbackRemoteEntity: ScoreFeedbackRemoteEntity): ScoreFeedbackEntity {
        try {
            return ScoreFeedbackEntity(
                scoreFeedback = scoreFeedbackRemoteEntity.scoreFeedback
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    @Throws(DataMappingException::class)
    fun transformFromEntity(scoreFeedbackEntity: ScoreFeedbackEntity): ScoreFeedbackRemoteEntity {
        try {
            return ScoreFeedbackRemoteEntity(
                scoreFeedback = scoreFeedbackEntity.scoreFeedback
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }
}