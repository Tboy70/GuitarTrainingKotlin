package thomas.guitartrainingkotlin.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import thomas.guitartrainingkotlin.data.db.entity.UserDBEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDBEntity")
    suspend fun retrieveUser(): UserDBEntity?

    @Insert
    suspend fun insertUser(userDBEntity: UserDBEntity)

    @Query("DELETE FROM UserDBEntity")
    suspend fun clearUser()

}