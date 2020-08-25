package thomas.guitartrainingkotlin.data.db.dao

import androidx.room.*
import thomas.guitartrainingkotlin.data.db.entity.SongDBEntity

@Dao
interface SongDao {

    @Query("SELECT * FROM SongDBEntity")
    fun retrieveSongList(): List<SongDBEntity>

    @Query("SELECT * FROM SongDBEntity WHERE idSong = :idSong")
    fun retrieveSongById(idSong: String): SongDBEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSong(songDBEntity: SongDBEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongList(songDBEntityList: List<SongDBEntity>)

    @Update
    fun updateSong(songDBEntity: SongDBEntity)

    @Query("DELETE FROM SongDBEntity WHERE idSong = :idSong")
    fun deleteSongById(idSong: String)

    @Query("DELETE FROM SongDBEntity")
    fun clearSong()
}