package thomas.guitartrainingkotlin.domain.interactor.song

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import thomas.guitartrainingkotlin.domain.model.Song
import thomas.guitartrainingkotlin.domain.repository.SongRepository
import thomas.guitartrainingkotlin.domain.repository.UserRepository
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class CreateSong @Inject constructor(
    private val userRepository: UserRepository,
    private val songRepository: SongRepository
) {

    suspend fun createSong(songToCreate: Song): Flow<Unit> {
        return userRepository.retrieveUserId().map {
            songToCreate.userId = it ?: ""
        }.flatMapMerge {
            songRepository.createSong(songToCreate)
        }
    }
}