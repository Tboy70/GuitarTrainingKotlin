package thomas.guitartrainingkotlin.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import thomas.guitartrainingkotlin.data.db.entity.ScoreDBEntity

@Dao
interface ScoreDao {

    @Query("SELECT * FROM ScoreDBEntity WHERE idSong = :idSong")
    fun retrieveSongScore(idSong: String): List<ScoreDBEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScoreDBEntityList(scoreDBEntityList: List<ScoreDBEntity>)

    @Query("DELETE FROM ScoreDBEntity")
    fun clearScore()
}