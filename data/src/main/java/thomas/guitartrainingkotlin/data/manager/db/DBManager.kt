package thomas.guitartrainingkotlin.data.manager.db

import thomas.guitartrainingkotlin.data.entity.db.SongDBEntity

interface DBManager {

    fun retrieveSongList(): List<SongDBEntity>
    fun insertSongList(songDBEntityList: List<SongDBEntity>)
    fun updateSong(songDBEntity: SongDBEntity)
    fun deleteSongById(idSong: String)
    fun deleteSong()
}