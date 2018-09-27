package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.SongEntity
import thomas.example.com.data.entity.remote.song.SongRemoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongRemoteEntityDataMapper @Inject constructor() {

    fun transformRemoteEntityToEntity(songRemoteEntity: SongRemoteEntity): SongEntity {
        val songEntity = SongEntity()
        songEntity.idSong = songRemoteEntity.idSong
        songEntity.titleSong = songRemoteEntity.titleSong
        songEntity.artistSong = songRemoteEntity.artistSong
        songEntity.scoreSong = songRemoteEntity.scoreSong
        songEntity.nbPlay = songRemoteEntity.nbPlay
        songEntity.idUser = songRemoteEntity.idUser

        return songEntity
    }

    fun transformListRemoteEntitiesToListEntities(songRemoteEntitiesList: List<SongRemoteEntity>): List<SongEntity> {
        val songEntitiesList: MutableList<SongEntity> = mutableListOf()

        for (songRemoteEntity: SongRemoteEntity in songRemoteEntitiesList) {
            songEntitiesList.add(transformRemoteEntityToEntity(songRemoteEntity))
        }
        return songEntitiesList
    }

    fun transformEntityToRemoteEntity(songEntity: SongEntity): SongRemoteEntity {
        val songRemoteEntity = SongRemoteEntity()
        songRemoteEntity.idSong = songEntity.idSong
        songRemoteEntity.titleSong = songEntity.titleSong
        songRemoteEntity.artistSong = songEntity.artistSong
        songRemoteEntity.scoreSong = songEntity.scoreSong
        songRemoteEntity.nbPlay = songEntity.nbPlay
        songRemoteEntity.idUser = songEntity.idUser

        return songRemoteEntity
    }

    fun transformListEntitiesToListRemoteEntities(songEntitiesList: List<SongEntity>): List<SongRemoteEntity> {
        val songRemoteEntitiesList: MutableList<SongRemoteEntity> = mutableListOf()

        for (songEntity: SongEntity in songEntitiesList) {
            songRemoteEntitiesList.add(transformEntityToRemoteEntity(songEntity))
        }
        return songRemoteEntitiesList
    }
}