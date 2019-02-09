package thomas.guitartrainingkotlin.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import thomas.guitartrainingkotlin.data.mapper.ScoreEntityDataMapper
import thomas.guitartrainingkotlin.data.mapper.ScoreFeedbackEntityDataMapper
import thomas.guitartrainingkotlin.data.mapper.SongEntityDataMapper
import thomas.guitartrainingkotlin.data.business.APIBusinessHelper
import thomas.guitartrainingkotlin.domain.model.Score
import thomas.guitartrainingkotlin.domain.model.ScoreFeedback
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongDataRepository @Inject constructor(
    private val apiBusinessHelper: APIBusinessHelper,
    private val songEntityDataMapper: SongEntityDataMapper,
    private val scoreFeedbackEntityDataMapper: ScoreFeedbackEntityDataMapper,
    private val scoreEntityDataMapper: ScoreEntityDataMapper
) : SongRepository {

    override fun retrieveSongListByUserId(userId: String): Single<List<Song>> {
        return Single.defer {
            apiBusinessHelper.retrieveSongListByUserId(userId).map {
                songEntityDataMapper.transformFromEntity(it)
            }
        }
    }

    override fun retrieveSongById(idSong: String): Single<Song> {
        return Single.defer {
            apiBusinessHelper.retrieveSongFromId(idSong).map {
                songEntityDataMapper.transformFromEntity(it)
            }
        }
    }

    override fun createSong(song: Song): Completable {
        return Completable.defer {
            apiBusinessHelper.createSong(songEntityDataMapper.transformToEntity(song))
        }
    }

    override fun updateSong(song: Song): Completable {
        return Completable.defer {
            apiBusinessHelper.updateSong(songEntityDataMapper.transformToEntity(song))
        }
    }

    override fun removeSong(idSong: String): Completable {
        return Completable.defer {
            apiBusinessHelper.removeSong(idSong)
        }
    }

    override fun sendScoreFeedback(scoreFeedback: ScoreFeedback, idSong: String): Completable {
        return Completable.defer {
            apiBusinessHelper.sendScoreFeedback(scoreFeedbackEntityDataMapper.transformModelToEntity(scoreFeedback), idSong)
        }
    }

    override fun retrieveSongScoreHistory(idSong: String): Single<List<Score>> {
        return Single.defer {
            apiBusinessHelper.retrieveSongScoreHistory(idSong).map {
                scoreEntityDataMapper.transformFromEntity(it)
            }
        }
    }
}