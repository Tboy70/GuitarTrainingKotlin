package thomas.guitartrainingkotlin.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import thomas.guitartrainingkotlin.data.business.APIBusinessHelper
import thomas.guitartrainingkotlin.data.mapper.ScoreEntityDataMapper
import thomas.guitartrainingkotlin.data.mapper.ScoreFeedbackEntityDataMapper
import thomas.guitartrainingkotlin.data.mapper.SongEntityDataMapper
import thomas.guitartrainingkotlin.domain.model.Score
import thomas.guitartrainingkotlin.domain.model.ScoreFeedback
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject
import javax.inject.Singleton

@FlowPreview
@ExperimentalCoroutinesApi
@Singleton
class SongDataRepository @Inject constructor(
    private val apiBusinessHelper: APIBusinessHelper,
    private val songEntityDataMapper: SongEntityDataMapper,
    private val scoreFeedbackEntityDataMapper: ScoreFeedbackEntityDataMapper,
    private val scoreEntityDataMapper: ScoreEntityDataMapper
) : SongRepository {

    override fun retrieveSongListByUserId(userId: String): Flow<List<Song>> {
        return apiBusinessHelper.retrieveSongListByUserId(userId).map {
            songEntityDataMapper.transformFromEntity(it)

        }
    }

    override fun retrieveSongById(idSong: String): Flow<Song> {
        return apiBusinessHelper.retrieveSongFromId(idSong).map {
            songEntityDataMapper.transformFromEntity(it)
        }
    }

    override fun createSong(song: Song): Flow<Unit> {
        return apiBusinessHelper.createSong(songEntityDataMapper.transformToEntity(song))
    }

    override fun updateSong(song: Song): Flow<Unit> {
        return apiBusinessHelper.updateSong(songEntityDataMapper.transformToEntity(song))

    }

    override fun removeSong(idSong: String): Flow<Unit> {
        return apiBusinessHelper.removeSong(idSong)
    }

    override fun sendScoreFeedback(scoreFeedback: ScoreFeedback, idSong: String): Flow<Unit> {
        return apiBusinessHelper.sendScoreFeedback(
            scoreFeedbackEntityDataMapper.transformModelToEntity(scoreFeedback),
            idSong
        )
    }

    override fun retrieveSongScoreHistory(idSong: String): Flow<List<Score>> {
        return apiBusinessHelper.retrieveSongScoreHistory(idSong).map {
            scoreEntityDataMapper.transformFromEntity(it)
        }

    }
}