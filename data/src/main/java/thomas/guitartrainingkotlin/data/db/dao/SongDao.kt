package thomas.guitartrainingkotlin.data.db.dao

import androidx.room.*
import thomas.guitartrainingkotlin.data.db.entity.SongDBEntity

@Dao
interface SongDao {

    @Query("SELECT * FROM SongDBEntity")
    suspend fun retrieveSongList(): List<SongDBEntity>

    @Query("SELECT * FROM SongDBEntity WHERE idSong = :idSong")
    suspend fun retrieveSongById(idSong: String): SongDBEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(songDBEntity: SongDBEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongList(songDBEntityList: List<SongDBEntity>)

    @Update
    suspend fun updateSong(songDBEntity: SongDBEntity)

    @Query("DELETE FROM SongDBEntity WHERE idSong = :idSong")
    suspend fun deleteSongById(idSong: String)

    @Query("DELETE FROM SongDBEntity")
    suspend fun clearSong()
}