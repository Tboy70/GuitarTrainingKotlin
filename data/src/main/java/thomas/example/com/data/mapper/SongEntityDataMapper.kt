package thomas.example.com.data.mapper

import thomas.example.com.data.entity.SongEntity
import thomas.example.com.model.Song
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongEntityDataMapper @Inject constructor() {

    fun transformEntityToModel(songEntity: SongEntity): Song {
        val song = Song()
        song.idSong = songEntity.idSong
        song.titleSong = songEntity.titleSong
        song.artistSong = songEntity.artistSong
        song.averageScoreSong = songEntity.averageScoreSong
        song.totalScoreSong = songEntity.totalScoreSong
        song.nbPlay = songEntity.nbPlay
        song.lastPlay = songEntity.lastPlay
        song.idUser = songEntity.idUser
        song.idInstrument = songEntity.idInstrument

        return song
    }

    fun transformListEntitiesToListModels(songEntityList: List<SongEntity>): List<Song> {
        val songList: MutableList<Song> = mutableListOf()

        for (songEntity: SongEntity in songEntityList) {
            songList.add(transformEntityToModel(songEntity))
        }
        return songList
    }

    fun transformModelToEntity(song: Song): SongEntity {
        val songEntity = SongEntity()

        songEntity.idSong = song.idSong
        songEntity.titleSong = song.titleSong
        songEntity.artistSong = song.artistSong
        songEntity.averageScoreSong = song.averageScoreSong
        songEntity.totalScoreSong = song.totalScoreSong
        songEntity.nbPlay = song.nbPlay
        songEntity.lastPlay = song.lastPlay
        songEntity.idUser = song.idUser
        songEntity.idInstrument = song.idInstrument

        return songEntity
    }

    fun transformListModelsToListEntities(songModelsList: List<Song>): List<SongEntity> {
        val songEntitiesList: MutableList<SongEntity> = mutableListOf()

        for (song: Song in songModelsList) {
            songEntitiesList.add(transformModelToEntity(song))
        }
        return songEntitiesList
    }
}