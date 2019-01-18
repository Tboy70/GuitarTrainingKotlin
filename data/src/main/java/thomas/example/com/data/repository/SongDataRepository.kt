package thomas.example.com.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import thomas.example.com.data.mapper.ScoreEntityDataMapper
import thomas.example.com.data.mapper.ScoreFeedbackEntityDataMapper
import thomas.example.com.data.mapper.SongEntityDataMapper
import thomas.example.com.data.business.APIBusinessHelper
import thomas.example.com.data.business.SongBusinessHelper
import thomas.example.com.model.Score
import thomas.example.com.model.ScoreFeedback
import thomas.example.com.model.Song
import thomas.example.com.repository.SongRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongDataRepository @Inject constructor(
    private val songBusinessHelper: SongBusinessHelper,
    private val apiBusinessHelper: APIBusinessHelper,
    private val songEntityDataMapper: SongEntityDataMapper,
    private val scoreFeedbackEntityDataMapper: ScoreFeedbackEntityDataMapper,
    private val scoreEntityDataMapper: ScoreEntityDataMapper
) : SongRepository {

    override fun retrieveSongsListByUserId(idUser: String): Single<List<Song>> {
        return Single.defer {
            apiBusinessHelper.retrieveSongsListByUserId(idUser).map {
                songEntityDataMapper.transformListEntitiesToListModels(it)
            }
        }
    }

    override fun retrieveSongById(idSong: String): Single<Song> {
        return Single.defer {
            apiBusinessHelper.retrieveSongFromId(idSong).map {
                songEntityDataMapper.transformEntityToModel(it)
            }
        }
    }

    override fun createSong(song: Song): Completable {
        return Completable.defer {
            apiBusinessHelper.createSong(songEntityDataMapper.transformModelToEntity(song))
        }
    }

    override fun updateSong(song: Song): Completable {
        return Completable.defer {
            apiBusinessHelper.updateSong(songEntityDataMapper.transformModelToEntity(song))
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

    override fun retrieveSongScoreHistoric(idSong: String): Single<List<Score>> {
        return Single.defer {
            apiBusinessHelper.retrieveSongScoreHistoric(idSong).map {
                scoreEntityDataMapper.transformListEntitiesToListModels(it)
            }
        }
    }
}