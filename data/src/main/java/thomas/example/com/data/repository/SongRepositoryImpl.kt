package thomas.example.com.data.repository

import io.reactivex.Observable
import thomas.example.com.data.mapper.SongEntityDataMapper
import thomas.example.com.data.repository.client.APIClient
import thomas.example.com.data.repository.client.SongClient
import thomas.example.com.model.Song
import thomas.example.com.repository.SongRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongRepositoryImpl @Inject constructor(private val songClient: SongClient,
                                             private val apiClient: APIClient,
                                             private val songEntityDataMapper: SongEntityDataMapper) : SongRepository {

    override fun createSong(song: Song): Observable<String> {
        return Observable.defer {
            apiClient.createSong(songEntityDataMapper.transformModelToEntity(song))
        }
    }

    override fun retrieveSongsListByUserId(idUser: String): Observable<List<Song>> {
        return Observable.defer {
            apiClient.retrieveSongsListByUserId(idUser).map {
                songEntityDataMapper.transformListEntitiesToListModels(it)
            }
        }
    }
}