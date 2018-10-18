package thomas.example.com.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import thomas.example.com.data.mapper.ScoreEntityDataMapper
import thomas.example.com.data.mapper.ScoreFeedbackEntityDataMapper
import thomas.example.com.data.mapper.SongEntityDataMapper
import thomas.example.com.data.repository.client.APIClient
import thomas.example.com.data.repository.client.SongClient
import thomas.example.com.model.Score
import thomas.example.com.model.ScoreFeedback
import thomas.example.com.model.Song
import thomas.example.com.repository.SongRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongRepositoryImpl @Inject constructor(
    private val songClient: SongClient,
    private val apiClient: APIClient,
    private val songEntityDataMapper: SongEntityDataMapper,
    private val scoreFeedbackEntityDataMapper: ScoreFeedbackEntityDataMapper,
    private val scoreEntityDataMapper: ScoreEntityDataMapper
) : SongRepository {

    override fun retrieveSongsListByUserId(idUser: String): Observable<List<Song>> {
        return Observable.defer {
            apiClient.retrieveSongsListByUserId(idUser).map {
                songEntityDataMapper.transformListEntitiesToListModels(it)
            }
        }
    }

    override fun retrieveSongById(idSong: String): Observable<Song> {
        return Observable.defer {
            apiClient.retrieveSongFromId(idSong).map {
                songEntityDataMapper.transformEntityToModel(it)
            }
        }
    }

    override fun createSong(song: Song): Completable {
        return Completable.defer {
            apiClient.createSong(songEntityDataMapper.transformModelToEntity(song))
        }
    }

    override fun updateSong(song: Song): Completable {
        return Completable.defer {
            apiClient.updateSong(songEntityDataMapper.transformModelToEntity(song))
        }
    }

    override fun removeSong(idSong: String): Observable<Boolean> {
        return Observable.defer {
            apiClient.removeSong(idSong)
        }
    }

    override fun sendScoreFeedback(scoreFeedback: ScoreFeedback, idSong: String): Observable<Boolean> {
        return Observable.defer {
            apiClient.sendScoreFeedback(scoreFeedbackEntityDataMapper.transformModelToEntity(scoreFeedback), idSong)
        }
    }

    override fun retrieveSongScoreHistoric(idSong: String): Observable<List<Score>> {
        return Observable.defer {
            apiClient.retrieveSongScoreHistoric(idSong).map {
                scoreEntityDataMapper.transformListEntitiesToListModels(it)
            }
        }
    }
}